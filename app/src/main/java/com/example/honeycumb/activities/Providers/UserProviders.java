package com.example.honeycumb.activities.Providers;

import com.example.honeycumb.activities.Utils.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/*logic crud firebase*/
/*store in colletion*/
public class UserProviders {

    private CollectionReference mCollection;


    public UserProviders() {
        mCollection=FirebaseFirestore.getInstance().collection("user");
    }

    /*method get info user*/
    public Task<DocumentSnapshot> getUser (String id){
        return  mCollection.document(id).get();
    }

    public Task<Void> create(User user){
        return  mCollection.document(user.getId()).set(user);
    }

    public Task<Void>updateC(User user){
        Map <String, Object> map=new HashMap<>();
        map.put("username",user.getUsername());
        return mCollection.document(user.getId()).update(map);
    }

    public  Task<Void> update(User user){/*matrix tipo string*/
        Map<String,Object> map=new HashMap<>();
        map.put("username" ,user.getUsername());/*se mapean los datos y se envian a la base de datos */
        map.put("email",user.getEmail());
        return mCollection.document(user.getId()).update(map);
    }

}
