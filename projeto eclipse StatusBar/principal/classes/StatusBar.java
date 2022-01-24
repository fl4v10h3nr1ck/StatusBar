






package classes;







import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;












public class StatusBar extends JFrame{

	


private static final long serialVersionUID = 1L;






private final int 												STATUS_MAX = 10;    
private int 														status_atual; 







// dimensões da barra de progresso
private int 														largura;
private int 														altura;
private int 														tam_bordas;
private int														tam_bloco;



private Color cor_bordas;
private Color cor_bloco_progresso;
private Color cor_fundo_esquerdo;
private Color cor_fundo_direito; 
private Color cor_texto_porcentagem;









/***************************************     início construtores ***************************************/










	public StatusBar(){
	
	this(300, 50);
	}



	
	
	
	
	
	
	
	
	
	
	public StatusBar( int largura, int altura){
		
				
			
	//this.largura = largura;
	this.altura = altura;
	
	//  o comprimento da borda deve ser um numero par
	this.tam_bordas =  (int)altura*20/100;
	if( this.tam_bordas%2 != 0)
	this.tam_bordas++;
	
	
	int aux = largura - 2*this.tam_bordas + (11*this.tam_bordas)/2;
	this.tam_bloco = aux/STATUS_MAX;
	
	
	this.largura = 2*this.tam_bordas + (11*this.tam_bordas)/2 + 10*this.tam_bloco;
	
	this.status_atual = 0;
			
		
	this.setSize( this.largura, this.altura);
	this.setResizable(false); 
	this.setLayout(null);
	this.setUndecorated(true);
	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setLocationRelativeTo(null);
	this.setAlwaysOnTop(true);    //faz essa janela sempre ficar na frente de todas as outras janelas.	
	
	
	setBarColor( null, null, null, null, null );

	}
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	

/***************************************   fim construtores ***************************************/
	
	
	
	
	
	
	
	
	
	
	public void setBarColor(     Color cor_bordas,
												  Color cor_bloco_progresso,
												  Color cor_fundo_esquerdo,
												  Color  cor_fundo_direito,
												  Color cor_texto_porcentagem){
	
	if( cor_bordas != null)	
	this.cor_bordas = cor_bordas;
	else
	this.cor_bordas = Color.black;
	
	if( cor_bloco_progresso != null)	
	this.cor_bloco_progresso = cor_bloco_progresso;
	else
	this.cor_bloco_progresso = Color.black;
	
	if( cor_fundo_esquerdo != null)	
	this.cor_fundo_esquerdo = cor_fundo_esquerdo;
	else
	this.cor_fundo_esquerdo = new Color(139, 134, 130);
	
	if( cor_fundo_direito != null)	
	this.cor_fundo_direito = cor_fundo_direito;
	else
	this.cor_fundo_direito = Color.white;
	
	if( cor_texto_porcentagem != null)	
	this.cor_texto_porcentagem = cor_texto_porcentagem;
	else
	this.cor_texto_porcentagem = Color.white;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private final void paint(){

		
	
	Graphics g = getBufferStrategy().getDrawGraphics();	    
	Graphics2D g2 = (Graphics2D) g.create(getInsets().right,
            getInsets().top,
            getWidth() - getInsets().left,
            getHeight() - getInsets().bottom);	
			
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
	
	g2.setPaint( this.cor_bordas);
	g2.fillRect(  0 , 0  , this.largura, this.altura );
	
	g2.setPaint( this.cor_texto_porcentagem);
	g2.setFont( new Font("Tahoma", 1, 13));	
	g2.drawString( "Aguarde...", this.tam_bordas + 15, this.tam_bordas + 12);
	g2.drawString( String.valueOf( (int)status_atual*100/STATUS_MAX)+"%", this.largura - this.tam_bordas - 50, this.tam_bordas + 12);
	
	
	GradientPaint paint  = new GradientPaint(   0, this.altura/2, 
																			   this.cor_fundo_esquerdo,
															this.largura, this.altura/2 ,this.cor_fundo_direito);	
	g2.setPaint(paint);
	

	
	int posicao_Y = this.tam_bordas + 20;
	int altura_barra_progresso = this.altura - 2*this.tam_bordas - 20;
	int largura_total_da_barra = this.largura - 2*this.tam_bordas;
	
	g2.fillRoundRect( this.tam_bordas, posicao_Y,
									largura_total_da_barra, 
									altura_barra_progresso ,
								  this.tam_bordas, this.tam_bordas);
	
	
	

	g2.setPaint( this.cor_bloco_progresso);
	
	
	
	int espacamento_entre_blocos = this.tam_bordas/2;
	
	for( int i = 0; i < STATUS_MAX && i < status_atual; i++)
	g2.fillRect( this.tam_bordas + espacamento_entre_blocos + i *(this.tam_bloco + espacamento_entre_blocos), 
	posicao_Y +2, this.tam_bloco,  altura_barra_progresso -4  );
	
	
	
	if (!getBufferStrategy().contentsLost())
	getBufferStrategy().show();


	g.dispose(); 
	g2.dispose();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// é usado o desenho direto para a barra de progresso
	
	public void paint( Graphics g){
		
	super.paint(g);	
	paint();	
	}
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	public void mostrar(){
		
	setVisible(true);	
	this.createBufferStrategy(2);
	paint();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	// atualiza a barra em 10%
	public void atualizar(){
		
	this.status_atual++;
	paint();
	}
	
	
	
	
	
	
	
	
	
	
	
	// atualiza a barra em x%
	public void atualizar( int x){
		
	this.status_atual += x;
	
	if( this.status_atual > this.STATUS_MAX)
	this.status_atual = this.STATUS_MAX;	
		
	paint();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	// limpa o status
	public void LimparStatus(){
		
	this.status_atual = 0;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
