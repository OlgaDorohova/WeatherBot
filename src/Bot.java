
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Bot extends TelegramLongPollingBot {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			TelegramBotsApi bot = new TelegramBotsApi(DefaultBotSession.class);
			bot.registerBot(new Bot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpdateReceived(Update update) {
		// TODO Auto-generated method stub

		var message = update.getMessage();
		var chatId = update.getMessage().getChatId();
		Model model = new Model();

		if (message != null && message.hasText())
			try {
				switch (message.getText()) {
				case "/help":
					sendText(chatId, "Чем могу помочь?");
					break;
				case "/setting":
					sendText(chatId, "Что настроить?");
					break;
				case "hello":
					sendText(chatId, "Hello!!!");
					break;

				default:
					try {
						sendText(chatId, Weather.getWeather(message.getText(), chatId, model));
					} catch (Exception e) {
						sendText(chatId, "Такой город не найден!");
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

	}

//	public void sendIcon(long chatId, String text) throws Exception {
//		Bot bot = new Bot();
//		SendMessage message = new SendMessage();
//		message.setChatId(chatId);
//		bot.send_photo(message.getChatId(), open("path/to/file","rb"), text);
//
//	}

	public void sendText(long chatId, String text) throws Exception {
		SendMessage msg = new SendMessage();
		msg.enableHtml(true);
		msg.setChatId(chatId);
		msg.setText(text);
		setButton(msg);
		try {
			execute(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setButton(SendMessage sendMessage) {
		ReplyKeyboardMarkup replyKeyboardMakeup = new ReplyKeyboardMarkup();
		sendMessage.setReplyMarkup(replyKeyboardMakeup);
		replyKeyboardMakeup.setSelective(true);
		replyKeyboardMakeup.setResizeKeyboard(true);
		replyKeyboardMakeup.setOneTimeKeyboard(false);

		List<KeyboardRow> keyboardRowsList = new ArrayList();

		KeyboardRow keyboardFirstRow = new KeyboardRow();
		keyboardFirstRow.add(new KeyboardButton("/help"));
		keyboardFirstRow.add(new KeyboardButton("/setting"));

		keyboardRowsList.add(keyboardFirstRow);

		replyKeyboardMakeup.setKeyboard(keyboardRowsList);

	}

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return "testbot10203040_bot";
	}

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return "5703273553:AAFfeybvzPgpi82d6hyhErxmTpVlilCqw8U";
	}

}
