
public class cosineSimilarityPlayground {
	
	
	public static void main(String[] args){
		System.out.println("hello");
		cosineTest();
	}
	
	public static void cosineTest(){
		
		cosineSimilarity cosine = new cosineSimilarity();
		
		double[] array1 = {13.5, 18.4, 19.6, 21.4, 14.8, 2.3};
		double[] array2 = {12.3, 5.9, 2.1};
		
		System.out.println(cosine.getCosineSimilarity(array1,array2));
		
		
		
		
		
	}

}
