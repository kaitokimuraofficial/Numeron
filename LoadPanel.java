import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoadPanel extends JPanel implements ActionListener{
    JPanel loadPart;
    JLabel jl;

    public LoadPanel() {
        setLayout(new BorderLayout());
        setSize(500, 350);
        
        loadPart = new JPanel();
        loadPart.setBackground(Color.BLUE);
        loadPart.setLayout(new BorderLayout());
        
        jl = new JLabel("NOW LOADING....");
        jl.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
        
        loadPart.add(jl, BorderLayout.CENTER);
        add(loadPart);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
}
