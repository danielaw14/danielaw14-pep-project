package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;


public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postResgisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessagesByIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessagesByIDHandler);
        app.patch("/messages/{message_id}", this::patchMessagesByIDHandler);
        app.get("/accounts/{account_id}/messages", this::getUserMessagesHandler);
        
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postResgisterHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account registeredAccount = accountService.register(account);
        if (registeredAccount != null){
            context.json(mapper.writeValueAsString(registeredAccount));
        }
        else{
            context.status(400);
        }
    }

    private void postLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account loginAttempt = accountService.login(account);
        if (loginAttempt != null){
            context.json(mapper.writeValueAsString(loginAttempt));
        }
        else{
            context.status(401);
        }
    }
    private void postMessagesHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message postedMessage = messageService.postMessage(message);

        if (postedMessage != null){
            context.json(mapper.writeValueAsString(postedMessage));
        }
        else
        {
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context){
        context.json(messageService.getAllMessages());
    }

    private void getMessagesByIDHandler(Context context) throws JsonProcessingException{
        context.json(messageService.getMessageByMessageID(Integer.parseInt(context.pathParam("message_id"))));
    }

    private void deleteMessagesByIDHandler(Context context) throws JsonProcessingException{
        context.json(messageService.DeleteMessagesByID(Integer.parseInt(context.pathParam("message_id"))));
    }

    private void patchMessagesByIDHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message updatedMessage = messageService.UpdateMessagesByID(message.getMessage_id(), message.getMessage_text());

        if (updatedMessage != null){
            context.json(mapper.writeValueAsString(updatedMessage));
        }
        else
        {
            context.status(400);
        }
    }

    private void getUserMessagesHandler(Context context) throws JsonProcessingException{
        context.json(messageService.getAllMessagesByUser(Integer.parseInt(context.pathParam("account_id"))));
    }

}