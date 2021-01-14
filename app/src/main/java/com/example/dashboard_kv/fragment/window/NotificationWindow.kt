package com.example.dashboard_kv.fragment.window

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dashboard_kv.R
import com.example.dashboard_kv.api.FtpFile
import com.example.dashboard_kv.api.Notification
import com.example.dashboard_kv.api.SupplyStaff

const val notification_title:String = "通知"
const val notification_windowKey : String ="notification"

class NotificationWindow(): BaseWindow(notification_title, notification_windowKey) {

    override var rootLayoutId: Int
        get() = R.layout.fragment_notification
        set(value) {}


    lateinit var  viewModel:NotificationViewModel

    lateinit var  listView_notification:ListView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel =  ViewModelProvider(this).get(NotificationViewModel::class.java)
            .also {

                it.notifications.observe(viewLifecycleOwner,
                object:Observer<MutableList<Notification>>{

                    override fun onChanged(t: MutableList<Notification>?) {


                        ( listView_notification?.adapter as ArrayAdapter<Notification>).apply {

                            this.clear()
                            this.addAll(t)

                        }

                    }

                });

            }

    }




    inner class NotificationArrayAdapter(@NonNull context : Context, @LayoutRes resource:Int):ArrayAdapter<Notification>(context,resource){

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
             //super.getView(position, convertView, parent)

            val notification = getItem(position)

            return layoutInflater.inflate(R.layout.widget_notification,null)
                .apply {

                    findViewById<TextView>(R.id.textView_notification_title)
                        .apply {
                            text = notification.title

                            setOnLongClickListener { v ->

                                TODO("显示通知详情!")

                            }
                        }


                    findViewById<TextView>(R.id.textView_notification_count)
                        .apply {
                            text = notification.feedbackNumber.toString()
                            setOnClickListener { v ->

                                TODO("弹出框显示反馈")

                            }
                        }


                }


        }

    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         val root:ViewGroup = super.onCreateView(inflater, container, savedInstanceState) as ViewGroup


        listView_notification =  root.findViewById<ListView>(R.id.listView_notifications)
            .apply{

                adapter =  NotificationArrayAdapter(requireContext(),R.layout.list_view_notification)

            }

        return root;
    }


    override fun onStart() {
        super.onStart()

        val n1 = Notification("","","","","","",23,
        1,"","","","","关于项目制改革通知事项","","",1)

        val n2 = Notification("","","","","","",5,
            2,"","","","","关于春节放假通知","","",2)


        viewModel.addNotifications(n1,n2)


    }

}

    /**
     * 消息通知的ViewModel
     */
 class NotificationViewModel : ViewModel() {

     val notifications = MutableLiveData<MutableList<Notification>>()

    init{
        notifications.value?:ArrayList<Notification>().apply { notifications.value = this }
    }

      fun setNotifications(ns:MutableList<Notification>){
          notifications.value = ns
      }

      fun addNotifications(vararg ns:Notification){
          for(n in ns)
          notifications.value?.add(n)

          notifications.value = notifications.value
      }

      fun addNotification(n:Notification ) {
          notifications.value?.add(n)

          notifications.value = notifications.value
      }

 }