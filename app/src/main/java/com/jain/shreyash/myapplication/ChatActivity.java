package com.jain.shreyash.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jain.shreyash.utils.Constants;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static in.shadowfax.proswipebutton.UiUtils.dpToPx;

public class ChatActivity extends AppCompatActivity {
    private List<ChatMessage> chatList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ChatAdapter mAdapter;
    private EditText message_edit_text;
    private Button send_text;
    String chat_message;
    SharedPreferences sharedPreferences;
    List<ChatMessage> chatDetailsArray;
    Boolean poll_active;
    TextView poll_title;
    int count;
    int other;
    int limiter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatDetailsArray = new ArrayList<>();
        message_edit_text=findViewById(R.id.edittext_chatbox);
        send_text=findViewById(R.id.button_chatbox_send);
        setTitle("Feedback & Suggestions Group");
        recyclerView = (RecyclerView) findViewById(R.id.reyclerview_message_list);

        LinearLayout layout = (LinearLayout) findViewById(R.id.ll_change_margin);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) layout.getLayoutParams();


        SharedPreferences a_d = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        String activity_date_old= a_d.getString("visit_date", "2010-04-12");
        SharedPreferences.Editor editor = a_d.edit();
        String activity_date_new = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        editor.putString("visit_date",activity_date_new);
        editor.apply();
        Date visit_old=new Date();
        SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");
        try {
            visit_old=formatter1.parse(activity_date_old);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        limiter=0;
        Date date1=new Date();
        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        String get_date= sp.getString("date_saved", "2010-04-12");

        try {
            date1=formatter1.parse(get_date);
            Date date2=new Date();

            long duration  = date2.getTime() - date1.getTime();
            long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
            if (diffInDays>1) limiter=1;
        } catch (ParseException e) {
            e.printStackTrace();
        }


        other=0;
        count=1;
        poll_title=findViewById(R.id.poll_title);
        sharedPreferences = this.getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
        final String user_name = sharedPreferences.getString(Constants.name,"");



        FirebaseDatabase BoardReference = FirebaseDatabase.getInstance();

        DatabaseReference mBoardReference = BoardReference.getReference("poll_sheet");
        poll_active=false;
        mBoardReference.child("poll1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                other++;
                getPollDetails message = dataSnapshot.getValue(getPollDetails.class);
                Log.e("current cancel stats",dataSnapshot.getKey());
                poll_active=message.poll_bool;
                Log.e("current cancel stats",poll_active+"");
                float dip = 245f;
                Resources r = getResources();
                float px = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        dip,
                        r.getDisplayMetrics()
                );
                int p=(int)(px);
                if (poll_active && count==1 && other==1 && limiter==1){
                    params.setMargins(5, 0, 0, p);
                    layout.setLayoutParams(params);

                    LayoutInflater inflater = LayoutInflater.from(getApplicationContext());

                    LinearLayout options_layout = (LinearLayout) findViewById(R.id.poll_layout_ll);
                    View to_add = inflater.inflate(R.layout.poll_layout, options_layout,false);

                    TextView poll_head=to_add.findViewById(R.id.poll_title);
                    poll_head.setText(message.poll_name);
                    RadioButton rg1=to_add.findViewById(R.id.choice_1);
                    RadioButton rg2=to_add.findViewById(R.id.choice_2);
                    RadioButton rg3=to_add.findViewById(R.id.choice_3);
                    rg1.setText(message.poll_item1);
                    rg2.setText(message.poll_item2);
                    rg3.setText(message.poll_item3);

                    ContentLoadingProgressBar pb1=to_add.findViewById(R.id.progress_choice_1);
                    ContentLoadingProgressBar pb2=to_add.findViewById(R.id.progress_choice_2);
                    ContentLoadingProgressBar pb3=to_add.findViewById(R.id.progress_choice_3);

                    float a1=message.poll_count1;
                    float a2=message.poll_count2;
                    float a3=message.poll_count3;
                    float total;
                    total=(a1+a2+a3);
                    a1=((float)a1/(float)total)*100;
                    a2=((float)a2/(float)total)*100;
                    a3=((float)a3/(float)total)*100;

                    Button btn=to_add.findViewById(R.id.vote);
                    options_layout.addView(to_add);
                    float finalA = a1;
                    float finalA1 = a2;
                    float finalA2 = a3;
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase BoardReference = FirebaseDatabase.getInstance();

                            DatabaseReference mBoardReference = BoardReference.getReference("poll_sheet");
                            DatabaseReference hopperRef = mBoardReference.child("poll1");
                            Map<String, Object> hopperUpdates = new HashMap<>();
                            if(rg1.isChecked()){



                                count++;
                                hopperUpdates.put("poll_count1", message.poll_count1+1);

                                hopperRef.updateChildren(hopperUpdates);
                                btn.setEnabled(false);
                            }
                            else if(rg2.isChecked()){
                                count++;

                                hopperUpdates.put("poll_count2", message.poll_count2+1);

                                hopperRef.updateChildren(hopperUpdates);
                                btn.setEnabled(false);
                            }
                            else if(rg3.isChecked())
                            { count++;
                                hopperUpdates.put("poll_count3", message.poll_count3+1);

                                hopperRef.updateChildren(hopperUpdates);
                                btn.setEnabled(false);}
                                else {
                                Toast.makeText(getApplicationContext(),"select an option",Toast.LENGTH_LONG).show();


                            }
                            pb1.setProgress((int) finalA);
                            pb2.setProgress((int) finalA1);
                            pb3.setProgress((int) finalA2);
                            SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                            editor.putString("date_saved",date);
                            editor.commit();
                            Toast.makeText(getApplicationContext(),"Response taken, and you know what its good to have your say !",Toast.LENGTH_LONG).show();

                        }
                    });




                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.e("current cancel stats 2",poll_active+"");


       FirebaseDatabase PostReference = FirebaseDatabase.getInstance();
        List<String> chatmessageArray=new ArrayList<>();

        DatabaseReference cPostReference = PostReference.getReference("chat_sheet");
        Date finalVisit_old = visit_old;
        cPostReference.
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ChatMessage that_chat=null;
                        chatDetailsArray.clear();
                        chatList.clear();

                        int c_pos=0;


                        chatmessageArray.add("initail one");

                        for (DataSnapshot child: dataSnapshot.getChildren()) {
                            Log.e("Count " ,""+child.getChildrenCount());
                            Log.e("please tell",""+ child.getKey());
                            ChatMessage this_chat=child.getValue(ChatMessage.class);
                           Log.e("please tell 2",""+ child.getValue(ChatMessage.class).getMsg_type());
                            if(child.getValue(ChatMessage.class).getname().equals(user_name)){
                               this_chat.msg_type=false;
                            this_chat.name="You";}
                           chatDetailsArray.add(this_chat);
                            SimpleDateFormat formatter2=new SimpleDateFormat("yyyy/M/d h:mm:ss a");

                            try {
                                Date mess_date=formatter2.parse(child.getValue(ChatMessage.class).gettime());

                                if(mess_date.before(finalVisit_old)) {
                                    c_pos++;
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            if(!child.getKey().equals("TAGGED")  )chatList.add(this_chat) ;
                          else{if(child.getValue(ChatMessage.class).getMsg_type())that_chat=this_chat;}

                        }

                      //  chat_text_end.setText("have your say...");
                        if(!(that_chat==null)){chatList.add(that_chat);}

                        Log.d("All chats",chatDetailsArray.toString());
                        mAdapter = new ChatAdapter(chatList);
                        chatDetailsArray.clear();

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        // recyclerView.addItemDecoration(new ActivityWorkers.GridSpacingItemDecoration(2, dpToPx(10), true));
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(mAdapter);
                        if(c_pos>chatList.size() - 1 || c_pos==0)
                        recyclerView.scrollToPosition(chatList.size() - 1);
                        else {recyclerView.scrollToPosition(c_pos);}
                        }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("activated or not", "loadPost:onCancelled", databaseError.toException());

                    }
                });
        //Log.d("All chats",chatDetailsArray.toString());




   send_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chat_message=message_edit_text.getText().toString();
                if(!chat_message.equals(""))
                {message_edit_text.getText().clear();
                uploadCancelDetails(chat_message);
                chatDetailsArray.clear();}
               /* FirebaseDatabase PostReference = FirebaseDatabase.getInstance();
                List<String> chatmessageArray=new ArrayList<>();
                DatabaseReference cPostReference = PostReference.getReference("chat_sheet");
                cPostReference.
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                chatDetailsArray.clear();



                                chatmessageArray.add("initail one");

                                for (DataSnapshot child: dataSnapshot.getChildren()) {
                                    Log.e("Count " ,""+child.getChildrenCount());
                                    Log.e("please tell",""+ child.getKey());
                                    ChatMessage this_chat=child.getValue(ChatMessage.class);
                                    Log.e("please tell 2",""+ child.getValue(ChatMessage.class).getmessage());
                                    chatDetailsArray.add(this_chat);
                                    chatmessageArray.add(child.getValue(ChatMessage.class).getmessage());
                                    chatList.add(this_chat);
                                }

                                ChatMessage movie = new ChatMessage("", "", "2015","",false);
                                chatList.add(movie);
                                Log.d("All chats",chatDetailsArray.toString());
                                mAdapter = new ChatAdapter(chatList);
                                chatDetailsArray.clear();

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(mLayoutManager);
                                // recyclerView.addItemDecoration(new ActivityWorkers.GridSpacingItemDecoration(2, dpToPx(10), true));
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(mAdapter);
                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w("activated or not", "loadPost:onCancelled", databaseError.toException());

                            }
                        });*/


            }
        });

    }



    private void uploadCancelDetails(String chat_message) {
        sharedPreferences = this.getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
        final String user_name = sharedPreferences.getString(Constants.name,"");;

        final DateFormat df = new SimpleDateFormat("yyyy/M/d h:mm:ss a");
      //  final String request_date = df.format(rq_date);

        final String email = sharedPreferences.getString(Constants.email,"");
        final String email_refined = email.replaceAll("\\W+", "");

        sharedPreferences = getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        //Time from server
        final Calendar calendar = Calendar.getInstance();
        DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset");
        offsetRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                double offset = snapshot.getValue(Double.class);
                double estimatedServerTimeMs = System.currentTimeMillis() + offset;
                calendar.setTimeInMillis(((long) estimatedServerTimeMs));
                calendar.setTimeInMillis(((long) estimatedServerTimeMs));
                Log.d("inter",""+calendar.getTime());
            }
            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");

            }
        });


        Log.d("inter after",""+calendar.getTime());

        Date d1=calendar.getTime();
        //Toast.makeText(this, ""+request_date, Toast.LENGTH_SHORT).show();
        FirebaseDatabase PostReference = FirebaseDatabase.getInstance();
        final DatabaseReference mPostReference = PostReference.getReference("chat_sheet");

       String special_text="";
        if(user_name.equals("Shreyash Jain")||user_name.equals("Tanish Jain")||user_name.equals("Himanshu Jain")){special_text="Sattvik Team";}
        final String key = mPostReference.push().getKey();
        //String finalSpecial_text = special_text;
        mPostReference.
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                       ChatMessage chatDetails;
                        //Creating record to Firebase
                        SimpleDateFormat format = new SimpleDateFormat("h:mm:ss a | d/M/yyyy");

                        String team="";
                        if(user_name.equals("Shreyash Jain")||user_name.equals("Tanish Jain")||user_name.equals("Himanshu Jain")){team="Sattvik Team";}
                        chatDetails = new ChatMessage(user_name,chat_message,  format.format(d1), team, true);
                        mPostReference.child(key).setValue(chatDetails);
                        String filename = "ChatData";
                        //Getting number of cancel requests
                        int count = 0;
                        /*try {
                            FileInputStream inStream = openFileInput(filename);
                            ObjectInputStream objectInStream = new ObjectInputStream(inStream);
                            count = objectInStream.readInt();// Get the number of cancel requests
                            objectInStream.close();
                        }
                        catch (Exception e) {
                            Log.e("reading error",""+e);
                            e.printStackTrace();
                        }
                        //Updating internal storage
                        FileOutputStream outStream;
                        try {
                            outStream = openFileOutput(filename, Context.MODE_PRIVATE);
                            ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);
                            // Save size first
                            int sz = count +1;
                            objectOutStream.writeInt(sz);
                            objectOutStream.writeObject(cancelDetails);
                            objectOutStream.close();
                            outStream.close();
                        } catch (Exception e) {
                            Log.e("writer error", e+"");
                            e.printStackTrace();
                        }*/


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(ChatActivity.this, "Unable to send request", Toast.LENGTH_LONG).show();
                        Log.w("cancel uplodaed or not", "loadPost:onCancelled", databaseError.toException());
                    }
                });
    }


}
