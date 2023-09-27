import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mychat.R
import com.example.mychat.model.User
import com.example.mychat.databinding.ItemUserBinding

class RecyclerViewAdapter(private val clickListener:(User)->Unit) : RecyclerView.Adapter<MyViewHolder>()
{
    private val userList = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemUserBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_user, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(userList[position], clickListener)
    }

    fun setList(users: List<User>){
        userList.clear()
        userList.addAll(users)
    }

    companion object {
        private const val TAG = "RecyclerViewAdapter"
    }
}

class MyViewHolder(val binding: ItemUserBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(user: User, clickListener: (User) -> Unit){
        binding.tvName.text = user.name
        binding.tvEmail.text = user.email
        binding.llListItem.setOnClickListener {
            clickListener(user)
        }
    }
}