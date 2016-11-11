
public class cosineSimilarity {

	public double calculateCosineSimilarity(double[] docVector1, double[] docVector2){
		
		double innerProduct = 0.0;
		double doc1Mag = 0.0;
		double doc2Mag = 0.0;
		double cosineSimilarity = 0.0;
		
		for (int i = 0; i < docVector1.length; i++){
			
			innerProduct += docVector1[i]*docVector2[i];
			doc1Mag += Math.pow(docVector1[i], 2);
			doc2Mag += Math.pow(doc2Mag[i], 2);
			
		}
		
		doc1Mag = Math.sqrt(doc1Mag);
		doc2Mag = Math.sqrt(doc2Mag);
		
		if (doc1Mag != 0.0 || doc2Mag != 0.0){
			cosineSimilarity = innerProduct/(doc1Mag * doc2Mag);
		}
		else{
			System.out.println("Magnitude equals 0.0");
			return 0.0;
		}
		
		
		
	}
	
	public double getCosineSimilarity(double[] docVector1, double[] docVector2){
		
		if (docVector1.length > docVector2.length){
			double[] newDocVector2 = new double[docVector1.length];
			
			for(int i = 0; i < newDocVector2.length;i++){
				newDocVector2[i] = 0.0;
			}
			for(int i = 0; i < docVector2.length; i++){
				newDocVector2[i] = docVector2[i];
			}
			System.out.println("Doc1 larger");
			System.out.println(docVector1.toString());
			return calculateCosineSimilarity(docVector1,newDocVector2);
		}
		
		else if (docVector1.length < docVector2.length){
			
			double[] newDocVector1 = new double[docVector2.length];
			
			for(int i = 0; i < newDocVector1.length; i++){
				newDocVector[i] = 0.0;
			}
			for(int i = 0; i < docVector1.length; i++ ){
				newDocVector1[i] = docVector1[i];
			}
			return calculateCosineSimilarity(newDocVector1,docVector2);
			
		}
		
		else return calculateCosineSimilarity(docVector1,docVector2);
		
		
	}
	

}
