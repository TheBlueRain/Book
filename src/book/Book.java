package book;


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
 

public class Book {
	
	static final int WIDTH = 640;
	static final int HEIGHT = 550;
	static final int TEST_COUNT = 10;
	
	protected Display display;
	public Shell shell;
	private TabFolder tabfolder;
	private TabItem tabitem1;
	private TabItem tabitem2;
	private TabItem tabitem3;
	private CCombo combo1;
	private Text theory_text;
	private CCombo combo2;
	private Composite images_compositeTh;
	private Composite images_compositeP;
	private Composite images_compositeT;
	private Composite test_composite; 

	private TestLogic test;
	private Label number_label;
	private Text answer_text;
	private Text question_text;
	private Button next_button;
	
	public static void main(String[] args) {
		Display display = new Display();
		Book book = new Book();
		book.open();
		while (!book.shell.isDisposed()) {
			if (!display.readAndDispatch ()) display.sleep (); 
      	} 
	}
	
	public void open() {
		display = Display.getCurrent();
		shell = new Shell(display);
        shell.setText("Учебник");
        shell.setSize(WIDTH, HEIGHT);
        if (initTabs()) {
	        createTheoryContent();
	        createPracticeContent();
	        createTestContent();
        }
        shell.open ();   
	}	

	private boolean initTabs() {
		try {
			tabfolder = new TabFolder(shell, SWT.NONE);
	        tabfolder.setSize(WIDTH - 5, HEIGHT - 35);
	        tabitem1 = new TabItem(tabfolder, SWT.NONE);
	        tabitem1.setText("Теория"); //theory
	        tabitem2 = new TabItem(tabfolder, SWT.NONE);
	        tabitem2.setText("Практика"); //practice
	        tabitem3 = new TabItem(tabfolder, SWT.NONE);
	        tabitem3.setText("Тест"); //test
		}
		catch (Exception e) {
			//TODO show error message 
			return false;
		}
		return true;
	}
	
	private void createTheoryContent() {
		Composite c1 = new Composite(tabfolder, SWT.BORDER | SWT.NONE);
        combo1 = new CCombo(c1, SWT.NONE);
        combo1.setSize(300, 30);
        combo1.setBounds(5, 5, 300, 30);
        theory_text = new Text(c1, SWT.BORDER | SWT.WRAP);
        theory_text.setSize(WIDTH - 25, HEIGHT - 355);
		theory_text.setBounds(5, 35, WIDTH - 25, HEIGHT - 355);
        tabitem1.setControl(c1);
        combo1.addSelectionListener(select_listener1);
        combo1.setItems(TheoryData.THEORY_TOPICS);
        combo1.select(0);
        images_compositeTh = new Composite(c1, SWT.BORDER | SWT.NONE);
        images_compositeTh.setSize(WIDTH - 25, HEIGHT - 105);
        images_compositeTh.setBounds(200, 250, WIDTH - 425, HEIGHT - 320);
        drawImage(images_compositeTh, "res/images/theory.jpg", 0);
        updateTheoryText();
	}
	
	private void updateTheoryText() {
		if (theory_text != null && combo1 != null)
			theory_text.setText(TheoryData.THEORY_TOPICS_CONTENTS[combo1.getSelectionIndex()]);
	}

	private SelectionListener select_listener1 = new SelectionListener() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			updateTheoryText();			
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent e) { }
	};
	
	private void createPracticeContent() {
		Composite composite2 = new Composite(tabfolder,  SWT.BORDER | SWT.NONE);
		combo2 = new CCombo(composite2, SWT.NONE);
        combo2.setSize(300, 30);
        combo2.setBounds(5, 5, 300, 30);
        tabitem2.setControl(composite2);
        combo2.addSelectionListener(select_listener2);
        combo2.setItems(PracticeData.PRACTICE_TOPICS);
        combo2.select(0);
        images_compositeP = new Composite(composite2, SWT.BORDER | SWT.NONE);
        images_compositeP.setSize(WIDTH - 25, HEIGHT - 105);
        images_compositeP.setBounds(5, 35, WIDTH - 25, HEIGHT - 105);
        updatePracticeContent();
	}
	private SelectionListener select_listener2 = new SelectionListener() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			updatePracticeContent();			
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent e) { }
	};
	
	private void updatePracticeContent() {
		if (images_compositeP != null && combo2 != null) {
			clearChildren(images_compositeP);
			drawImage(images_compositeP, PracticeData.PRACTICE_TOPICS_IMAGE_URLS1[combo2.getSelectionIndex()], 0);
			drawImage(images_compositeP, PracticeData.PRACTICE_TOPICS_IMAGE_URLS2[combo2.getSelectionIndex()], 1);
		}
	}
	
	private void clearChildren(Composite c) {
		Control[] children = c.getChildren();
		for (int i = 0; i < children.length; i++) {
	          children[i].dispose();
	        }
	}
	
	private void drawImage(Composite comp, final String image_url, int top) {
		Canvas canvas = new Canvas(comp, SWT.NONE);
        canvas.setSize(600, 260);
        canvas.setBounds(0, top * 260, 600, 260);
        canvas.addPaintListener(new PaintListener() {
    		public void paintControl(PaintEvent e) {
		        Image image = null;
		        try {
		          image = new Image(display, image_url);	          
		        } catch (Exception e1) {	         
		          e1.printStackTrace();
		        }
		        e.gc.drawImage(image, 0, 0);
		        image.dispose();
			}
	    });
	}
	
	private void createTestContent() {
		test_composite = new Composite(tabfolder, SWT.BORDER | SWT.NONE);
        prepareBeginTest();
        tabitem3.setControl(test_composite);
        images_compositeT = new Composite(test_composite, SWT.BORDER | SWT.NONE);
        images_compositeT.setSize(WIDTH - 425, HEIGHT - 320);
        images_compositeT.setBounds(200, 250, WIDTH - 450, HEIGHT - 320);
        drawImage(images_compositeT, "res/images/pract.jpg", 0);
	}

	private void prepareBeginTest() {
		if (test_composite == null) 
			return;
		Label l1 = new Label(test_composite, SWT.NONE);
        l1.setSize(600, 30);
        l1.setBounds(10, 20, 600, 30);
        l1.setText("Чтобы проверить себя, пройдите тест.");
        Button button = new Button(test_composite, SWT.PUSH);
        button.setSize(120, 45);
        button.setBounds(10, 50, 120, 45);
        button.setText("Начать тест");
        button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {			
				beginTest();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
		});
	}
	
	private void createTestFields() {
		number_label = new Label(test_composite, SWT.NONE);
        number_label.setSize(400, 25);
        number_label.setBounds(10, 20, 400, 25);
        number_label.setText("Задание №1:");
        question_text = new Text (test_composite, SWT.BORDER | SWT.WRAP);
        question_text.setSize(600, 60);
        question_text.setBounds(10, 50, 600, 40);
        Label answer_label = new Label(test_composite, SWT.NONE);
        answer_label.setSize(400, 25);
        answer_label.setBounds(10, 100, 400, 25);
        answer_label.setText("Ваш ответ:");
        answer_text = new Text(test_composite, SWT.BORDER);
        answer_text.setSize(400, 25);
        answer_text.setBounds(10, 125, 400, 25);
        next_button = new Button(test_composite, SWT.PUSH);
        next_button.setSize(120, 45);
        next_button.setBounds(10, 155, 130, 45);
        next_button.setText("Следующий");
        next_button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				nextClick();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
		});
        images_compositeT = new Composite(test_composite, SWT.BORDER | SWT.NONE);
        images_compositeT.setSize(WIDTH - 425, HEIGHT - 320);
        images_compositeT.setBounds(200, 250, WIDTH - 450, HEIGHT - 320);
        drawImage(images_compositeT, "res/images/pract.jpg", 0);
   	}

	private void beginTest() {
		if (test_composite == null) 
			return;
		clearChildren(test_composite);
		createTestFields();
		test = new TestLogic(TEST_COUNT);
        number_label.setText("Задание №1:");        
        question_text.setText(test.getNextQuestion());
        answer_text.setText("");
	}
	
	private void nextClick() {
		test.checkAnswer(answer_text.getText());
		String next_q = test.getNextQuestion();
		if (next_q != null) {
			number_label.setText("Задание №" + String.valueOf(test.currentIndex + 1) + ":");        
			question_text.setText(next_q);
        	answer_text.setText("");	
		} else { 
			showResult();
			return;
		}		
	}

	private void showResult() {
		clearChildren(test_composite);
		Label l1 = new Label(test_composite, SWT.NONE);
        l1.setSize(400, 30);
        l1.setBounds(10, 20, 400, 30);
        l1.setText("Ваш результат: " + test.getGrade() + ". Пройти тест ещё раз?");
        Button button = new Button(test_composite, SWT.PUSH);
        button.setSize(120, 45);
        button.setBounds(10, 50, 120, 45);
        button.setText("Начать тест");
        button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {			
				beginTest();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
		});
	}
}
