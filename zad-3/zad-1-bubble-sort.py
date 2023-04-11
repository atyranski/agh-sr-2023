import logging
import random

import numpy as np
import ray
import cProfile

if ray.is_initialized:
    ray.shutdown()
ray.init(logging_level=logging.ERROR)

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

# array_1 = np.array([random.randrange(1, 99, 1) for i in range(5)])
# print(array_1)
# array_1_result = local_bubble_sort(array_1)
# print(array_1_result)

original = np.array([random.randrange(1, 99, 1) for i in range(50_000)])
print(original)

# local_result = local_bubble_sort(original)
# print("Local:", local_result)
cProfile.run("local_bubble_sort(original)")

# remote_result = ray.get(remote_bubble_sort.remote(original))
# print("Remote:", remote_result)
cProfile.run("ray.get(remote_bubble_sort.remote(original))")

ray.shutdown()
