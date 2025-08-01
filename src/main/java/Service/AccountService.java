package Service;

import Model.Account;
import DAO.AccountDAO;


public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account register(Account account)
    {
        if ((account.username != null) && (account.password.length() >= 4) 
            && (accountDAO.getUserByUsername(account.getUsername()) == null))
        {
            return accountDAO.register(account.getUsername(), account.getPassword());
        }
        else{
            return null;
        }
        
    }

    public Account login(Account account)
    {
        return accountDAO.login(account.getUsername(), account.getPassword());
    }
}
