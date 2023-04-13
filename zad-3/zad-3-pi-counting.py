# Exercise 1.1) Try using local bubble sort and remote bubble sort, show difference

import logging
import random

import numpy as np
import ray
import cProfile

if __name__ == '__main__':
    if ray.is_initialized:
        ray.shutdown()
    ray.init(logging_level=logging.ERROR)


    cProfile.run("")

    ray.shutdown()
