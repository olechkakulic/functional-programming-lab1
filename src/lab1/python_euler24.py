import math

def nth_permutation(digits, n):
    n -= 1
    result = []
    elems = digits[:]
    while elems:
        f = math.factorial(len(elems) - 1)
        index = n // f
        result.append(str(elems.pop(index)))
        n %= f
    return "".join(result)

if __name__ == "__main__":
    digits = list(range(10))
    print(nth_permutation(digits, 1_000_000))
