# Exercise 2.1) Create large lists and python dictionaries, put them in object store. Write a Ray task to process them.
import array
import logging
import random

import ray
import cProfile


@ray.remote
def process_data(numbers_list: array, numbers_dict: dict):
    return [numbers_list[i] * numbers_dict[i] for i in range(len(large_list))]


if __name__ == '__main__':
    if ray.is_initialized:
        ray.shutdown()
    ray.init(logging_level=logging.ERROR)

    size = 1_000_000
    large_list = [random.randrange(1, 99, 1) for i in range(size)]
    large_dict = {i: i % 2 for i in range(size)}

    large_list_ref = ray.put(large_list)
    large_dict_ref = ray.put(large_dict)

    retrieved_list = ray.get(large_list_ref)

    cProfile.run("ray.get(process_data.remote(large_list_ref, large_dict_ref))")

    ray.shutdown()
