package com.example.wangchang.testbottomnavigationbar;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.wangchang.testbottomnavigationbar.Been.AccountBeen;
import com.example.wangchang.testbottomnavigationbar.Been.TaskBeen;

import com.example.wangchang.testbottomnavigationbar.AccountBeenDao;
import com.example.wangchang.testbottomnavigationbar.TaskBeenDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig accountBeenDaoConfig;
    private final DaoConfig taskBeenDaoConfig;

    private final AccountBeenDao accountBeenDao;
    private final TaskBeenDao taskBeenDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        accountBeenDaoConfig = daoConfigMap.get(AccountBeenDao.class).clone();
        accountBeenDaoConfig.initIdentityScope(type);

        taskBeenDaoConfig = daoConfigMap.get(TaskBeenDao.class).clone();
        taskBeenDaoConfig.initIdentityScope(type);

        accountBeenDao = new AccountBeenDao(accountBeenDaoConfig, this);
        taskBeenDao = new TaskBeenDao(taskBeenDaoConfig, this);

        registerDao(AccountBeen.class, accountBeenDao);
        registerDao(TaskBeen.class, taskBeenDao);
    }
    
    public void clear() {
        accountBeenDaoConfig.clearIdentityScope();
        taskBeenDaoConfig.clearIdentityScope();
    }

    public AccountBeenDao getAccountBeenDao() {
        return accountBeenDao;
    }

    public TaskBeenDao getTaskBeenDao() {
        return taskBeenDao;
    }

}
