package com.etdvlpr.letstalk.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.etdvlpr.letstalk.data.dao.UserMessageDao;

public class ConversationListViewModelFactory implements ViewModelProvider.Factory {
    private UserMessageDao userMessageDao;

    public ConversationListViewModelFactory(UserMessageDao userMessageDao) {
        this.userMessageDao = userMessageDao;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ConversationListViewModel(userMessageDao);
    }
}
