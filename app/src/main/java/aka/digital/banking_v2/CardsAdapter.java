package aka.digital.banking_v2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardViewHolder> {

    private List<CardModel> cards;

    public CardsAdapter(List<CardModel> cards){
        this.cards = cards;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardModel card = cards.get(position);
        holder.txtType.setText(card.type);
        holder.txtBalance.setText(card.balance);
        holder.txtNumber.setText(card.number);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        TextView txtType, txtBalance, txtNumber;

        public CardViewHolder(@NonNull View itemView){
            super(itemView);
            txtType = itemView.findViewById(R.id.txtCardType);
            txtBalance = itemView.findViewById(R.id.txtCardBalance);
            txtNumber = itemView.findViewById(R.id.txtCardNumber);
        }
    }
}
