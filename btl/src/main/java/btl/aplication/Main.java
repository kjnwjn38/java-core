package btl.aplication;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
    	System.out.println("hhahah");
        String fileInputPath = "input.txt";
        ArrayList<Object> input = Utils.readFileInput(fileInputPath);

        ArrayList<BigInteger> operandArr = new ArrayList<>();
        ArrayList<Character> operatorArr = new ArrayList<>();

        for (int j = 0; j < input.size(); j++) {
            if (input.get(j) instanceof BigInteger) {
                operandArr.add(((BigInteger) input.get(j)));
            }
            else if (input.get(j) instanceof Character) {
                operatorArr.add(((Character) input.get(j)));
            }
        }

        BigInteger output = BigInteger.computeValue(operandArr, operatorArr);
        String fileOutputPath = "output.txt";
        Utils.writeFileOutput(output, fileOutputPath);
    }
}