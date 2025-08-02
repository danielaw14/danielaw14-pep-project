package Service;

import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message postMessage(Message message)
    {
        if (message.getMessage_text() != null && message.getMessage_text() != "" && message.getMessage_text().length() <= 255 
            && accountDAO.getUserByAccountID(message.posted_by) != null)
        {
            return messageDAO.postMessage(message);
        }
        else
        {
            return null;
        }
    }

    public List<Message> getAllMessages()
    {
        List<Message> messages = messageDAO.getAllMessages();
        return messages;
    }

    public Message getMessageByMessageID(int message_id)
    {
        Message messages = messageDAO.getMessageByMessageID(message_id);
        return messages;
    }

    public Message DeleteMessagesByID(int message_id){
        Message messages = messageDAO.DeleteMessagesByID(message_id);
        return messages;
    }

    public Message UpdateMessagesByID(int message_id, String message_text){
        if (message_text != null && message_text != "" &&  message_text.length() <= 255 
            && messageDAO.getMessageByMessageID(message_id) != null){
                return messageDAO.UpdateMessagesByID(message_id, message_text);
        }
        else
        {
            return null;
        }
    }

    public List<Message> getAllMessagesByUser(int posted_by)
    {
        List<Message> messages = messageDAO.getAllMessagesByUser(posted_by);
        return messages;
    }
}
