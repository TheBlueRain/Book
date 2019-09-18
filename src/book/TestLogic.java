package book;

import java.util.ArrayList;
import java.util.Random;

public class TestLogic {

	public int questionsCount;
	public int currentIndex;
	private ArrayList<QAPair> questionsCollection;
	private int[] answersMap;

	public TestLogic(int count) {
		this.questionsCount = count;
		this.currentIndex = -1;
		answersMap = new int[questionsCount];
		generateQuestionsCollection();
	}
	
	private void generateQuestionsCollection() {
		questionsCollection = new ArrayList<QAPair>();
		 Random ran = new Random();
		 for (int i=0; i<questionsCount/5; i++) {		
			 int random_index1 = ran.nextInt(getMaxQuestionsCount());
			 String q1= TestData.QUESTIONS1[random_index1];
			 String a1 = TestData.ANSWERS1[random_index1];
			 questionsCollection.add(new QAPair(q1,a1));
			 int random_index2 = ran.nextInt(getMaxQuestionsCount());
			 String q2 = TestData.QUESTIONS2[random_index2];
			 String a2 = TestData.ANSWERS2[random_index2];
			 questionsCollection.add(new QAPair(q2,a2));
			 int random_index3 = ran.nextInt(getMaxQuestionsCount());
			 String q3 = TestData.QUESTIONS3[random_index3];
			 String a3 = TestData.ANSWERS3[random_index3];
			 questionsCollection.add(new QAPair(q3,a3));
			 int random_index4 = ran.nextInt(getMaxQuestionsCount());
			 String q4 = TestData.QUESTIONS4[random_index4];
			 String a4 = TestData.ANSWERS4[random_index4];
			 questionsCollection.add(new QAPair(q4,a4));
			 int random_index5 = ran.nextInt(getMaxQuestionsCount());
			 String q5 = TestData.QUESTIONS5[random_index5];
			 String a5 = TestData.ANSWERS5[random_index5];
			 questionsCollection.add(new QAPair(q5,a5));
		 }
	}
	
	public boolean checkAnswer(String a) {   
		boolean r = false;
		if (currentIndex > -1 && currentIndex < questionsCount 
				&& questionsCollection != null && answersMap != null
				&& questionsCollection.get(currentIndex).a.equals(a)) {
			answersMap[currentIndex] = 1;
		}
		return r;
	}
	
	public String getNextQuestion() {
		if (currentIndex < questionsCount - 1) {
			currentIndex++;
			return questionsCollection.get(currentIndex).q;
		}
		else
			return null;
	}
	
	public String getGrade() {
		int sum = 0;
		for (int i=0; i<questionsCount; i++) {	
			sum += answersMap[i];
		}
		float percentage = (sum / (float)questionsCount ) * 100;
		if (percentage > 89)
			return "нркхвмн - 5";
		else if (percentage > 69)
			return "унпньн - 4";
		else if (percentage > 49)
			return "сднбкербнпхрекэмн - 3";
		else
			return "месднбкербнпхрекэмн";
	}

	/*** static ***/
	public static int getMaxQuestionsCount() {
		int count = TestData.QUESTIONS1.length;
		if (TestData.ANSWERS1.length < count)
			count = TestData.ANSWERS1.length;
		return count;
	}
	
	/*** class QAPair ***/
	class QAPair {
		String q;
		String a;
		
		public QAPair(String question, String answer) {
			this.q = question;
			this.a = answer;
		}	
	}
}
