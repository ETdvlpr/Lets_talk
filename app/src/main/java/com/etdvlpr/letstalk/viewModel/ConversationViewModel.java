package com.etdvlpr.letstalk.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.etdvlpr.letstalk.data.dao.MessageDao;
import com.etdvlpr.letstalk.data.dao.UserMessageDao;
import com.etdvlpr.letstalk.data.model.Message;
import com.etdvlpr.letstalk.data.model.UserMessage;
import com.etdvlpr.letstalk.util.SharedPref;

public class ConversationViewModel extends ViewModel {
    public final LiveData<PagedList<Message>> messageList;
    private MessageDao messageDao;

    public ConversationViewModel(MessageDao messageDao, String user) {
        this.messageDao = messageDao;
        messageList = new LivePagedListBuilder<>(messageDao.getMessageList(SharedPref.userName, user),10).build();
    }
}
