package com.etdvlpr.letstalk.ui.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.etdvlpr.letstalk.data.DataRepository;
import com.etdvlpr.letstalk.data.model.Message;
import com.etdvlpr.letstalk.data.model.User;
import com.etdvlpr.letstalk.data.model.UserMessage;

import java.util.List;

public class ConversationListViewModel extends AndroidViewModel {
    private LiveData<List<UserMessage>> conversationList;
    private DataRepository mRepository;



    public ConversationListViewModel (Application application) {
        super(application);
        mRepository = DataRepository.getInstance(application);
        conversationList = mRepository.getConversationList();
    }

    public LiveData<List<UserMessage>> getConversationList() {
        if (conversationList == null) {
            conversationList = new MutableLiveData<List<UserMessage>>();
            loadUsers();
        }
        return conversationList;
    }

    private void loadUsers() {
        // Do an asynchronous operation to fetch users.
    }


    public void insert(Message msg) { mRepository.insertMessage(msg); }
}
