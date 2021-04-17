package com.etdvlpr.letstalk.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.etdvlpr.letstalk.data.dao.MessageDao;
import com.etdvlpr.letstalk.data.dao.UserMessageDao;

public class ConversationViewModelFactory implements ViewModelProvider.Factory {
    private MessageDao messageDao;
    private String user;

    public ConversationViewModelFactory(MessageDao messageDao, String user) {
        this.messageDao = messageDao;
        this.user = user;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ConversationViewModel(messageDao, user);
    }
}
