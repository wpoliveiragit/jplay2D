package br.com.wellington.projetos.game.telas;

import java.awt.Color;
import java.awt.event.KeyEvent;

import br.com.wellington.jplay2D.audio.Audio;
import br.com.wellington.jplay2D.image.GameImage;
import br.com.wellington.projetos.game.cenarios.Cenario01;
import br.com.wellington.projetos.game.constants.Constants;
import br.com.wellington.projetos.game.controles.ControleVolume;
import br.com.wellington.projetos.game.utils.VerticalOptionsMenu;
import br.com.wellington.projetos.game.utils.TextWindow;
import projetos.jogoDaMemoria.GameControl;

public class TelaInicial extends GameControl {

	private VerticalOptionsMenu menu;
	private TextWindow start;
	private TextWindow options;
	private TextWindow credits;

	private GameImage logoJogo;

	private Audio musica;

	public TelaInicial() {
		super(30);
	}

	@Override
	protected void update() {
	}

	@Override
	protected void draw() {
		WINDOW.clear(Color.black);
		logoJogo.draw();
		start.draw();
		options.draw();
		credits.draw();
	}

	@Override
	protected void beforeStart() {
	}

	@Override
	protected void afterStart() {
		WINDOW.exit();
	}

	@Override
	protected void loadResources() {
		// [Keyboard]
		KEYBOARD.cleanKeys();
		KEYBOARD.addKeyBehaviorActuatorRequest(KeyEvent.VK_ESCAPE);
		KEYBOARD.addKeyBehaviorActuatorRequest(KeyEvent.VK_ENTER);
		KEYBOARD.addKeyBehaviorActuatorRequest(KeyEvent.VK_UP);
		KEYBOARD.addKeyBehaviorActuatorRequest(KeyEvent.VK_DOWN);
		KEYBOARD.addKeyBehaviorActuatorRequest(KeyEvent.VK_LEFT);
		KEYBOARD.addKeyBehaviorActuatorRequest(KeyEvent.VK_RIGHT);
		// [Mouse]
		MOUSE.setCursorImage(Constants.IMG_MOUSE);

		// [Audio]

		// [GameObject - logo]
		logoJogo = new GameImage(Constants.IMG_LOGO_JOGO);
		logoJogo.x = WINDOW.getJFrame().getWidth() - logoJogo.width - 20;
		logoJogo.y = 20;

		// [TextWindow]
		start = new TextWindow(Constants.COR_ON, Constants.FIXEDSYS_40, "START");
		start.x = (WINDOW.getJFrame().getWidth() - start.getWidth()) / 2;
		start.y = (WINDOW.getJFrame().getHeight() / 2);

		options = new TextWindow(Constants.COR_OFF, Constants.FIXEDSYS_40, "OPTIONS");
		options.x = (WINDOW.getJFrame().getWidth() - options.getWidth()) / 2;
		options.y = (WINDOW.getJFrame().getHeight() / 2) + options.getHeight();

		credits = new TextWindow(Constants.COR_OFF, Constants.FIXEDSYS_40, "CREDITS");
		credits.x = (WINDOW.getJFrame().getWidth() - credits.getWidth()) / 2;
		credits.y = (WINDOW.getJFrame().getHeight() / 2) + 2 * credits.getHeight();

		// [GERAL]
		menu = new VerticalOptionsMenu(3);
		musica = new Audio(Constants.SND_ENTRADA_MEGAMAN);
		musica.setLoop(true);
		ControleVolume.setMaxVolume((int) -musica.getMinimumVolume());
		musica.setVolume(musica.getMinimumVolume() + ControleVolume.getVolume());
		musica.play();
	}

	@Override
	protected void control() {
		if (KEYBOARD.checkKey(KeyEvent.VK_ESCAPE)) {// encerra o jogo
			super.exist();
			return;
		}
		if (KEYBOARD.checkKey(KeyEvent.VK_ENTER)) {
			switch (menu.getSelecao()) {
			case 0:// [START]
				musica.stop();
				new Cenario01().start();
				loadResources();
				break;
			case 1:// [OPTIONS]
				musica.stop();
				new TelaOptions().start();
				loadResources();
				break;
			case 2:// [CREDITS]
				break;
			}
			return;
		}

		// [CONTROLE]
		if (KEYBOARD.checkKey(KeyEvent.VK_UP)) {// [SOBE]
			menu.sobe();
			ajustaSelecao();
			return;
		} else if (KEYBOARD.checkKey(KeyEvent.VK_DOWN)) {// [DESCE]
			menu.desce();
			ajustaSelecao();
			return;
		}

	}

	private void ajustaSelecao() {
		switch (menu.getSelecao()) {
		case 0:
			start.setColorOn();
			options.setColorOff();
			credits.setColorOff();
			break;
		case 1:
			start.setColorOff();
			options.setColorOn();
			credits.setColorOff();
			break;
		case 2:
			start.setColorOff();
			options.setColorOff();
			credits.setColorOn();
			break;
		}
		new Audio(Constants.SND_SELECAO).play();
	}

}
