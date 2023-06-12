import javax.swing.*;
import java.awt.*;

public class LoadPanel extends JPanel {
    private JLabel loadLabel;

    public LoadPanel() {
        setLayout(new BorderLayout());
        setSize(500, 350);

        setBackground(Color.BLUE);
        setLayout(new BorderLayout());

        loadLabel = new JLabel("NOW LOADING....");
        loadLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));

        add(loadLabel, BorderLayout.CENTER);
    }
    
    /* ^^^^^^^^^^^^各種メソッドまとめ^^^^^^^^^^^^ */

    /* -------------getメソッド------------- */
    public JLabel getLoadLabel() {
        return loadLabel;
    }

    /* -------------setメソッド------------- */
    public void setLoadLabel(String str) {
        loadLabel.setText(str);
    }
}