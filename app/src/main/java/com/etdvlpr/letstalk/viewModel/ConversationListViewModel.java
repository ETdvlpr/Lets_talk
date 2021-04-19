package com.etdvlpr.letstalk.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.etdvlpr.letstalk.data.dao.UserMessageDao;
import com.etdvlpr.letstalk.data.model.UserMessage;
import com.etdvlpr.letstalk.util.SharedPref;

public class ConversationListViewModel extends ViewModel {
    public final LiveData<PagedList<UserMessage>> conversationList;
    private UserMessageDao userMessageDao;

    public ConversationListViewModel (UserMessageDao userMessageDao) {
        this.userMessageDao = userMessageDao;
        conversationList = new LivePagedListBuilder<>(userMessageDao.getConversationList(SharedPref.userName),10).build();
    }
}
