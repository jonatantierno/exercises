import random

INVALID_VALUE = -1
REPEAT_DRAW = 0

def rand5():
    """ Function that returns a random value between 1 and 5

    To use as base for the rand7() target function
    :return: random integer between 1 and 5.
    """
    return random.randint(1,5)

def rand7():
    """ Function that returns a random value between 1 and 7

    Uses rand5() as base.
    :return: random integer between 1 and 7
    """
    dr = DerivatedRand()
    return dr.rand7()

class DerivatedRand:
    """ Class that derivates the rand7 from the ran5 function.

    We use this class just to mock the rand7 function and perform proper unit testing.
    """

    def rand5(self):
        """
        Just call rand5.

        :return: random integer 1-5
        """
        return rand5()

    def rand7(self):
        """
        Convert two calls of the rand5 function into a 1-7 random number

        Two throws of the dice make for 25 possibilities. Remove the last 4 by repeating the draw, then we have 21
        possibilities, which we can use to create our 1-7 random number.

        :return: random integer 1-7
        """
        seq = convert_to_seq(self.rand5(),self.rand5())
        value = convert_seq_to_value(seq)

        if value == REPEAT_DRAW:
            return self.rand7()

        else:
            return value;


def convert_to_seq(n1,n2):
    """ This function turns  two random numbers from rand5() into one sequence number between 0 and 24.

    :param n1: First random value
    :param n2: Second random value
    :return: Sequence number between 0 and 24
    """
    return 5*(n1 - 1) + (n2 - 1)

def convert_seq_to_value(n):
    """ This function converts the sequence number (0-20) in an integer between 1 and 7.

    In order to obtain a probability of 1/7, we turn the numbers 0-20 into 1-7, and repeat the draw if the number is
    between 21 and 24. To indicate this we return 0.

    If the input is not in the valid range of 0-24, return -1.

    :return: integer between 1 and 7, 0 if new draw needed, -1 otherwise
    """
    if 0 <= n <= 20:
        return (n % 7) + 1

    elif 20 < n < 25:
        return REPEAT_DRAW

    else:
        return INVALID_VALUE

def test_rand7():
    """
    Function to test that the probabilities are approx 1/7.

    :return:list with number of results for each value 1-7. They should all be around 1000.
    """
    results = [0,0,0,0,0,0,0]

    for i in range(0,7000,1):
        value = rand7();
        results[value-1] = results[value-1]+1

    print(results)

if __name__ == '__main__':
    print(rand7())