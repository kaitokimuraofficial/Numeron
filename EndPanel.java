import javax.swing.*;
import java.awt.*;


public class EndPanel extends JPanel {
    JPanel endPart;

    JLabel jl;

    public EndPanel() {
        setLayout(new BorderLayout());
        setSize(500, 350);
        jl = new JLabel("END");
        endPart = new JPanel();

        endPart.setPreferredSize(new Dimension(500, 100));

        endPart.add(jl);
        add(endPart, BorderLayout.CENTER);
    }
}
