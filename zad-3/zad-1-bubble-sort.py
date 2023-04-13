# Exercise 1.1) Try using local bubble sort and remote bubble sort, show difference

import logging
import random

import numpy as np
import ray
import cProfile


def local_bubble_sort(array: np.array):
    result = np.array(array, copy=True)

    for i in range(np.size(result)):
        for j in range(np.size(result) - i - 1):
            if result[j] > result[j + 1]:
                temp = result[j]
                result[j] = result[j + 1]
                result[j + 1] = temp

    return result


@ray.remote
def remote_bubble_sort(array: np.array):
    result = np.array(array, copy=True)

    for i in range(np.size(result)):
        for j in range(np.size(result) - i - 1):
            if result[j] > result[j + 1]:
                temp = result[j]
                result[j] = result[j + 1]
                result[j + 1] = temp

    return result


if __name__ == '__main__':
    if ray.is_initialized:
        ray.shutdown()
    ray.init(logging_level=logging.ERROR)

    original = np.array([random.randrange(1, 99, 1) for i in range(25_000)])
    print("Original array:", original)

    cProfile.run("local_bubble_sort(original)")
    cProfile.run("ray.get(remote_bubble_sort.remote(original))")

    ray.shutdown()
