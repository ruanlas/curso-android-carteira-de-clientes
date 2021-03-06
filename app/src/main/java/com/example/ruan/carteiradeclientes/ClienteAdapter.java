package com.example.ruan.carteiradeclientes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruan.carteiradeclientes.dominio.entidade.Cliente;

import java.util.List;

// Esta classe armazenará os dados do RecycleView
// [ https://developer.android.com/guide/topics/ui/layout/recyclerview?hl=pt-br ]
public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolderCliente> {
                                // Estamos definindo como padrão para o adapter o
                                // ViewHolderCliente que criamos no final desta classe

    private List<Cliente> dados;

    public ClienteAdapter(List<Cliente> dados){
        this.dados = dados;
    }

//    @NonNull
//    @Override
//    public ClienteAdapter.ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ClienteAdapter.ViewHolderCliente viewHolder, int i) {
//
//    }

    @NonNull
    @Override
    public ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Este método será responsável por criar a refência da classe ViewHolderCliente

        // Na linha abaixo estamos criando uma referência do layoutInflater que é
        // responsável por "inflar" o layout
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        // Na linha abaixo o objeto view receberá a referência do layout 'linha_clientes.xml'
        View view = layoutInflater.inflate(R.layout.linha_clientes, viewGroup, false);
        /*
        * O método inflate() espera 3 parâmetros:
        * 1º => id do layout
        * 2º => referência do objeto viewGroup, ou seja, a 'view pai' que a linha estará vinculada,
        * que será o layout do componente recycleView
        * 3º => valor booleano. 'false' se deseja-se que a classe Adapter vincule-se automaticamente
        * à nossa view o nosso layout ao nosso recycleView
        * */
        ViewHolderCliente viewHolderCliente = new ViewHolderCliente(view, viewGroup.getContext());
        return viewHolderCliente;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCliente viewHolderCliente, int position) {
        // Este método é executado logo após a execução do método 'onCreateViewHolder', logo quando
        // é criado uma instância ViewHolderCliente

        // Neste método, os campos da classe ViewHolder serão preenchidos com os dados contidos
        // na lista de clientes

        if ( (dados != null ) && ( dados.size() > 0 )) {
            Cliente cliente = dados.get(position); //o parâmetro 'position' é referente à posição da
            // linha que está sendo desenhada naquele momento no objeto recycleView

            viewHolderCliente.txtLinhaNomeCliente.setText(cliente.nome);
            viewHolderCliente.txtLinhaTelefoneCliente.setText(cliente.telefone);
        }

    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public class ViewHolderCliente extends RecyclerView.ViewHolder{
        // Esta classe ViewHolderCliente será responsável por recuperar as referências do layout
        // criado no arquivo 'linha_clientes.xml', ou seja, buscará as referências da interface.

        public TextView txtLinhaNomeCliente, txtLinhaTelefoneCliente;

        public ViewHolderCliente(@NonNull View itemView, final Context context) {
            super(itemView);

            txtLinhaNomeCliente = (TextView) itemView.findViewById(R.id.txtLinhaNomeCliente);
            txtLinhaTelefoneCliente = (TextView) itemView.findViewById(R.id.txtLinhaTelefoneCliente);

            // Abaixo está sendo criado um evento de clique para a linha
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // verifica se existe registros
                    if (dados.size() > 0){

                        int itemSelecionado = getLayoutPosition(); //retorna a posição do item que
                        // foi selecionado no recycleView, que será a mesma posição na lista de dados
                        Cliente cliente = dados.get(itemSelecionado);// retorna o objeto contido na
                        // posição selecionada
//                        Toast.makeText(context, "Cliente: " + cliente.nome, Toast.LENGTH_LONG).show();

                        // para poder utilizar o objeto context dentro desta classe anônima,
                        // ele deve ser declarado como final
                        Intent intent = new Intent(context, ActCadCliente.class);

                        intent.putExtra("cliente", cliente); // o método putExtra serve para
                        // passar parâmetros para a outra activity que está sendo chamada

                        // para chamar outra Activity, utilizando o método startActivityForResult
                        // deve-se fazer um 'cast' no objeto context para AppCompatActivity
                        // O parâmetro 0 do requestCode se refere ao padrão adotado ao chamar a tela
                        // ActCadCliente, para garantir que o método onActivityResult seja executado
                        // para atualizar a listagem
                        ((AppCompatActivity)context).startActivityForResult(intent, 0);
                    }

                }
            });

        }
    }
}
