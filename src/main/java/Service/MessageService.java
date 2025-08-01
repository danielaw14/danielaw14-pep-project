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
        if (message.getMessage_text() != null && message.getMessage_text().length() <= 255 
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

    public Message getMessageByMessageID(Message message)
    {
        return messageDAO.getMessageByMessageID(message.getMessage_id());
    }

    public Message DeleteMessagesByID(Message message){
        return messageDAO.DeleteMessagesByID(message);
    }

    public Message UpdateMessagesByID(Message message){
        if (message.getMessage_text() != null && message.getMessage_text().length() <= 255 
            && messageDAO.getMessageByMessageID(message.getMessage_id()) != null){
                return messageDAO.UpdateMessagesByID(message);
        }
        else
        {
            return null;
        }
    }

    public List<Message> getAllMessagesByUser(Message message)
    {
        return messageDAO.getAllMessagesByUser(message.getPosted_by());
    }
}
