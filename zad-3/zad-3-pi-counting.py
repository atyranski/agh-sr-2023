# 3.3 Take a look on implement parallel Pi computation
# based on https://docs.ray.io/en/master/ray-core/examples/highly_parallel.html
#
# Implement calculating pi as a combination of actor (which keeps the
# state of the progress of calculating pi as it approaches its final value)
# and a task (which computes candidates for pi)

import logging
import random

import numpy as np
import ray
import cProfile


@ray.remote
class PiActor:
    def __init__(self):
        self.points_inside_circle = 0
        self.points_total = 0

    def update(self, points_inside_circle, points_total):
        self.points_inside_circle += points_inside_circle
        self.points_total += points_total

    def get_estimated_value(self):
        assert self.points_total > 0

        return (self.points_inside_circle * 4) / self.points_total


@ray.remote
def get_inside_circle_points_amount(sample_count):
    points_inside_circle = 0

    for i in range(sample_count):
        x = random.random()
        y = random.random()

        if x ** 2 + y ** 2 <= 1:
            points_inside_circle += 1

    return points_inside_circle


def estimate_pi_value(sample_count: int, workers_amount: int):
    pi_actor_handler = PiActor.remote()

    workers_refs = [get_inside_circle_points_amount.remote(sample_count) for _ in range(workers_amount)]

    for result_ref in workers_refs:
        points_inside_circle = ray.get(result_ref)
        pi_actor_handler.update.remote(points_inside_circle, sample_count)

    return ray.get(pi_actor_handler.get_estimated_value.remote())


if __name__ == '__main__':
    if ray.is_initialized:
        ray.shutdown()
    ray.init(logging_level=logging.ERROR)

    sample_count = 1_000_000
    workers_amount = 100

    cProfile.run("print(estimate_pi_value(sample_count, workers_amount))")

    ray.shutdown()
