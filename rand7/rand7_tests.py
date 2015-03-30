import unittest
import rand7
from mock import MagicMock

class TestRand7(unittest.TestCase):

    def test_convert_to_seq_0(self):
        self.assertEquals(0,rand7.convert_to_seq(1,1));

    def test_convert_to_seq_12(self):
        self.assertEquals(12,rand7.convert_to_seq(3,3));

    def test_convert_to_seq_24(self):
        self.assertEquals(24,rand7.convert_to_seq(5,5));

    def test_convert_seq_to_value_1(self):
        self.assertEquals(1,rand7.convert_seq_to_value(0));

    def test_convert_seq_to_value_7(self):
        self.assertEquals(7,rand7.convert_seq_to_value(6));

    def test_convert_seq_to_value_12(self):
        self.assertEquals(6,rand7.convert_seq_to_value(12));

    def test_convert_seq_to_value_21(self):
        self.assertEquals(0,rand7.convert_seq_to_value(21));

    def test_convert_seq_to_value_24(self):
        self.assertEquals(0,rand7.convert_seq_to_value(24));

    def test_convert_seq_to_value_25(self):
        self.assertEquals(-1,rand7.convert_seq_to_value(25));

    def test_convert_seq_to_value_minus5(self):
        self.assertEquals(-1,rand7.convert_seq_to_value(-5));

    def test_when_random_is_3_then_result_is_6(self):

        randomUnderTest = rand7.DerivatedRand()
        randomUnderTest.rand5 = MagicMock(side_effect=[3, 3])

        self.assertEquals(6,randomUnderTest.rand7());

        self.assertEquals(2,len(randomUnderTest.rand5.mock_calls));


    def test_seq_is_23_then_perform_another_draw(self):

        randomUnderTest = rand7.DerivatedRand()
        randomUnderTest.rand5 = MagicMock(side_effect=[5, 3, 3, 3])

        self.assertEquals(6,randomUnderTest.rand7());

        self.assertEquals(4,len(randomUnderTest.rand5.mock_calls));

if __name__ == '__main__':
    unittest.main()