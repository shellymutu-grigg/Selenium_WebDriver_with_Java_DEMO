package abstractComponents;

public class HelperFunctions {
	
	public int determineIndex(String productName) {
		int index =0;
		if(productName.contains("Previously viewed")) {
			index = 1;
		}
		return index;
	}
}
