import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainFrame extends JFrame {
    private static final int WIDTH = 650;
    private static final int HEIGHT = 500;

    private final JTextField textFieldX;
    private final JTextField textFieldY;
    private final JTextField textFieldZ;
    private final JTextField textFieldResult;

    private final ButtonGroup radioButtons = new ButtonGroup();
    private final Box boxFormulaType = Box.createHorizontalBox();
    private int formulaId = 1;

    private final ButtonGroup radioMemButtons = new ButtonGroup();
    private final Box boxMemType = Box.createHorizontalBox();
    private int memId = 1;

    private final JTextField textFieldMem1;
    private final JTextField textFieldMem2;
    private final JTextField textFieldMem3;

    private final JLabel formulaImageLabel;
    private final ImageIcon formula1Image;
    private final ImageIcon formula2Image;

    public Double calculate1(Double x, Double y, Double z) {
        double numerator = Math.sin(Math.PI * y * y) + Math.log(y * y);
        double denominator = Math.sin(Math.PI * z * z) + Math.sin(x) + Math.log(z * z) + x * x + Math.pow(Math.E, Math.cos(z * x));
        return numerator / denominator;
    }

    public Double calculate2(Double x, Double y, Double z) {
        double numerator = Math.pow(Math.cos(Math.pow(Math.E, y)) + Math.pow(Math.E, y * y) + Math.sqrt(1 / x), 0.25);
        double denominator = Math.pow(Math.cos(Math.PI * z * z * z) + Math.log(1 + z) * Math.log(1 + z), Math.sin(y));
        return numerator / denominator;
    }

    private void addRadioButton(String buttonName, final int formulaId) {
        JRadioButton button = new JRadioButton(buttonName);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                MainFrame.this.formulaId = formulaId;
                updateFormulaImage();
            }
        });
        radioButtons.add(button);
        boxFormulaType.add(button);
    }

    private void addRadioMemButton(String buttonName, final int memId) {
        JRadioButton button = new JRadioButton(buttonName);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                MainFrame.this.memId = memId;
            }
        });
        radioMemButtons.add(button);
        boxMemType.add(button);
    }

    private void updateFormulaImage() {
        if (formulaId == 1) {
            formulaImageLabel.setIcon(formula1Image);
        } else if (formulaId == 2) {
            formulaImageLabel.setIcon(formula2Image);
        }
    }

    public MainFrame() {
        super("Вычисление формулы");
        setSize(WIDTH, HEIGHT);

        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH)/2,
                (kit.getScreenSize().height - HEIGHT)/2);

        boxFormulaType.add(Box.createHorizontalGlue());
        addRadioButton("Формула 1", 1);
        addRadioButton("Формула 2", 2);
        radioButtons.setSelected(
                radioButtons.getElements().nextElement().getModel(), true);
        boxFormulaType.add(Box.createHorizontalGlue());
        boxFormulaType.setBorder(
                BorderFactory.createLineBorder(Color.RED));

        formula1Image = new ImageIcon("resources/Formula1.png");
        formula2Image = new ImageIcon("resources/Formula2.png");
        if (formula1Image.getIconWidth() == -1) {
            System.out.println("Ошибка загрузки изображения Формула 1");
        }
        if (formula2Image.getIconWidth() == -1) {
            System.out.println("Ошибка загрузки изображения Формула 2");
        }

        formulaImageLabel = new JLabel(formula1Image); // По умолчанию формула 1
        Box boxImage = Box.createHorizontalBox();
        boxImage.add(Box.createHorizontalGlue());
        boxImage.add(formulaImageLabel);
        boxImage.add(Box.createHorizontalGlue());
        boxImage.setBorder(BorderFactory.createLineBorder(Color.ORANGE));

        JLabel labelForX = new JLabel("X:");
        textFieldX = new JTextField("0", 10);
        textFieldX.setMaximumSize(textFieldX.getPreferredSize());
        JLabel labelForY = new JLabel("Y:");
        textFieldY = new JTextField("0", 10);
        textFieldY.setMaximumSize(textFieldY.getPreferredSize());
        JLabel labelForZ = new JLabel("Z:");
        textFieldZ = new JTextField("0", 10);
        textFieldZ.setMaximumSize(textFieldZ.getPreferredSize());

        Box boxVariables = Box.createHorizontalBox();
        boxVariables.setBorder(
                BorderFactory.createLineBorder(Color.YELLOW));
        boxVariables.add(Box.createHorizontalGlue());
        boxVariables.add(labelForX);
        boxVariables.add(Box.createHorizontalStrut(10));
        boxVariables.add(textFieldX);
        boxVariables.add(Box.createHorizontalStrut(100));
        boxVariables.add(labelForY);
        boxVariables.add(Box.createHorizontalStrut(10));
        boxVariables.add(textFieldY);
        boxVariables.add(Box.createHorizontalStrut(100));
        boxVariables.add(labelForZ);
        boxVariables.add(Box.createHorizontalStrut(10));
        boxVariables.add(textFieldZ);
        boxVariables.add(Box.createHorizontalGlue());

        JLabel labelForResult = new JLabel("Результат:");
        textFieldResult = new JTextField("0", 15);
        textFieldResult.setMaximumSize(
                textFieldResult.getPreferredSize());

        Box boxResult = Box.createHorizontalBox();
        boxResult.add(Box.createHorizontalGlue());
        boxResult.add(labelForResult);
        boxResult.add(Box.createHorizontalStrut(10));
        boxResult.add(textFieldResult);
        boxResult.add(Box.createHorizontalGlue());
        boxResult.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        JButton buttonCalc = new JButton("Вычислить");
        buttonCalc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    Double x = Double.parseDouble(textFieldX.getText());
                    Double y = Double.parseDouble(textFieldY.getText());
                    Double z = Double.parseDouble(textFieldZ.getText());
                    Double result;
                    if (formulaId==1)
                        result = calculate1(x, y, z);
                    else
                        result = calculate2(x, y, z);
                    textFieldResult.setText(result.toString());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JButton buttonReset = new JButton("Очистить поля");
        buttonReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                textFieldX.setText("0");
                textFieldY.setText("0");
                textFieldZ.setText("0");
                textFieldResult.setText("0");
            }
        });

        Box boxButtons = Box.createHorizontalBox();
        boxButtons.add(Box.createHorizontalGlue());
        boxButtons.add(buttonCalc);
        boxButtons.add(Box.createHorizontalStrut(30));
        boxButtons.add(buttonReset);
        boxButtons.add(Box.createHorizontalGlue());
        boxButtons.setBorder(
                BorderFactory.createLineBorder(Color.BLUE));

        JLabel labelForMem1 = new JLabel("Перем.1:");
        textFieldMem1 = new JTextField("0", 15);
        textFieldMem1.setMaximumSize(textFieldMem1.getPreferredSize());
        JLabel labelForMem2 = new JLabel("Перем.2:");
        textFieldMem2 = new JTextField("0", 15);
        textFieldMem2.setMaximumSize(textFieldMem2.getPreferredSize());
        JLabel labelForMem3 = new JLabel("Перем.3:");
        textFieldMem3 = new JTextField("0", 15);
        textFieldMem3.setMaximumSize(textFieldMem3.getPreferredSize());

        Box boxMemory = Box.createHorizontalBox();
        boxMemory.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
        //boxMemory.add(Box.createHorizontalGlue());
        boxMemory.add(labelForMem1);
        boxMemory.add(Box.createHorizontalStrut(5));
        boxMemory.add(textFieldMem1);
        //boxMemory.add(Box.createHorizontalStrut(5));
        boxMemory.add(Box.createHorizontalGlue());
        boxMemory.add(labelForMem2);
        boxMemory.add(Box.createHorizontalStrut(5));
        boxMemory.add(textFieldMem2);
        //boxMemory.add(Box.createHorizontalStrut(5));
        boxMemory.add(Box.createHorizontalGlue());
        boxMemory.add(labelForMem3);
        boxMemory.add(Box.createHorizontalStrut(5));
        boxMemory.add(textFieldMem3);
        //boxMemory.add(Box.createHorizontalGlue());

        boxMemType.add(Box.createHorizontalGlue());
        addRadioMemButton("Переменная 1", 1);
        addRadioMemButton("Переменная 2", 2);
        addRadioMemButton("Переменная 3", 3);
        radioMemButtons.setSelected(
                radioMemButtons.getElements().nextElement().getModel(), true);
        boxMemType.add(Box.createHorizontalGlue());
        boxMemType.setBorder(
                BorderFactory.createLineBorder(Color.PINK));

        JButton buttonM = new JButton("M+");
        buttonM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                Double mem;
                Double count;
                Double result = Double.parseDouble(textFieldResult.getText());
                if (memId==1) {
                    mem = Double.parseDouble(textFieldMem1.getText());
                    count = mem + result;
                    textFieldMem1.setText(count.toString());
                }
                else if (memId==2){
                    mem = Double.parseDouble(textFieldMem2.getText());
                    count = mem + result;
                    textFieldMem2.setText(count.toString());
                }
                else{
                    mem = Double.parseDouble(textFieldMem3.getText());
                    count = mem + result;
                    textFieldMem3.setText(count.toString());
                }
            }
        });

        JButton buttonMC = new JButton("MC");
        buttonMC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if (memId==1)
                    textFieldMem1.setText("0");
                else if (memId==2)
                    textFieldMem2.setText("0");
                else
                    textFieldMem3.setText("0");
            }
        });

        Box boxMemButtons = Box.createHorizontalBox();
        boxMemButtons.add(Box.createHorizontalGlue());
        boxMemButtons.add(buttonM);
        boxMemButtons.add(Box.createHorizontalStrut(30));
        boxMemButtons.add(buttonMC);
        boxMemButtons.add(Box.createHorizontalGlue());
        boxMemButtons.setBorder(
                BorderFactory.createLineBorder(Color.CYAN));

        Box contentBox = Box.createVerticalBox();
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(boxFormulaType);
        contentBox.add(boxImage);
        contentBox.add(boxVariables);
        contentBox.add(boxResult);
        contentBox.add(boxButtons);
        contentBox.add(boxMemory);
        contentBox.add(boxMemType);
        contentBox.add(boxMemButtons);
        contentBox.add(Box.createVerticalGlue());
        getContentPane().add(contentBox, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}