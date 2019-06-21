public class MeusPets extends AppCompatActivity {

    //Classe que fará a adaptação dos contatos para um ListView personalizado
    public class AnimaisAdapter extends BaseAdapter {

        private final ArrayList<Animal> animais;
        private final Activity activity;

        public AnimaisAdapter(ArrayList<Animal> animais, Activity activity) {
            this.animais = animais;
            this.activity = activity;
        }

        @Override
        public int getCount() {
            return animais.size();
        }

        @Override
        public Object getItem(int position) {
            return animais.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = activity.getLayoutInflater().inflate(R.layout.activity_exemplo_meu_animal, parent, false);

            final Animal animal = animais.get(position);

            //Instanciando as Views

            final TextView especie  = view.findViewById(R.id.lbl_especie);
            final TextView idade    = view.findViewById(R.id.lbl_idade);
            final TextView raca     = view.findViewById(R.id.lbl_raca);
            final TextView email    = view.findViewById(R.id.lbl_email);
            final TextView telefone = view.findViewById(R.id.lbl_telefone);
            final Button Editar = view.findViewById(R.id.botaoEditar);
            final Button Visualizar = view.findViewById(R.id.botaoVisualizar);

            //colocando a imagem
            final String image_url = URL_IMAGEM + animal.getImageName();
            final ImageView imgView = view.findViewById(R.id.img_animal);
            Picasso.with(CatalogoAnimais.this).load(image_url).into(imgView);

            //Definindo os valores para as Views
            especie.setText(animal.getEspecie());
            idade.setText( Integer.toString( animal.getIdade() ) );
            raca.setText( animal.getRaca() );
            email.setText( animal.getDono().getEmail() );
            telefone.setText( animal.getDono().getTelefone() );

            //fazendo o click do botao de visualizar
            Visualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Indo para a próxima tela
                    Intent intent = new Intent(MeusPets.this, VisualizarAnimal.class);

                    //colocando o shsaredPreferences para a proxima tela saber a qual animal estamos nos referindo
                    SharedPreferences preferencias = getSharedPreferences("Animal", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencias.edit();
                    editor.putString("id_animal", Integer.toString( animal.getIdAnimal() ) );
                    editor.apply();

                    startActivity(intent);
                }
            });

            //fazendo  o click do botato de editar
            Editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Indo para a próxima tela
                    Intent intent = new Intent(MeusPets.this, EditarCadastroAnimal.class);

                    //colocando o shsaredPreferences para a proxima tela saber a qual animal estamos nos referindo
                    SharedPreferences preferencias = getSharedPreferences("Animal", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencias.edit();
                    editor.putString("id_animal", Integer.toString( animal.getIdAnimal() ) );
                    editor.apply();

                    startActivity(intent);
                }
            });
        }
    }

    //Array que conterá os animais exibidos na lista
    private ArrayList<Animal> animais = new ArrayList<>();

    //Lista de animais
    private ListView listaAnimais;

    //ArrayAdapter para exibir os animais no ListView Personalizado
    private AnimaisAdapter animaisArrayAdapter;

    private String id_pessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_animais);

        //instancia a lista
        listaAnimais = findViewById(R.id.lista_animais);

        

        carregaAnimais();

    }

    //Função responsável por carregar os animais da pessoa na lista
    private void carregaAnimais() {

        //pegando o id da pessoa pelo SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("Pessoa", Context.MODE_PRIVATE);

        String id_pessoa_ = "";
        if(sharedPreferences.contains("id_pessoa")){

            id_pessoa_ = sharedPreferences.getString("id_pessoa","");

        }else{
            //se nao tem o sharedPreferences vai pra tela de login
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }

        final String id_pessoa = id_pessoa_;

        //Instancia o ArrayAdapter de usuários para a lista de contatos
        animaisArrayAdapter = new AnimaisAdapter(animais, CatalogoAnimais.this);

        //Seta o ArrayAdapter para a lista de animais
        listaAnimais.setAdapter(animaisArrayAdapter);

        //pegando os animais para adocao noa banco de dados com string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.URL_MEUS_ANIMAIS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){

                                //pegando o numero de animais que conseguimos encontrar
                                int tamanho = obj.getInt("num") ;

                                //pegando todos os animais e colocando em animais
                                for(int i = 0; i < tamanho; i++){
                                    Animal aux = new Animal();

                                    JSONObject jsonAux = obj.getJSONObject( Integer.toString(i) );
                                    //colocando os valores do jsonAux no Animal aux
                                    aux.setIdAnimal( jsonAux.getInt("id_animal" ) );
                                    aux.setNome( jsonAux.getString("nome" ) );
                                    aux.setEspecie( jsonAux.getString("especie" ) );
                                    aux.setDescricao( jsonAux.getString("descricao" ) );
                                    aux.setSexo( jsonAux.getString("sexo" ) );
                                    aux.setIdade( jsonAux.getInt("idade") );
                                    aux.setRaca( jsonAux.getString("raca" ) );
                                    aux.setImageName( jsonAux.getString("image_name" ) );
                                    aux.setCondicao( jsonAux.getString("condicao" ) );

                                    //vendo se eh uma Pessoa ou uma Ong
                                    int id_juridica_, id_fisica_;
                                    id_juridica_ = jsonAux.getInt("id_juridica");
                                    id_fisica_   = jsonAux.getInt("id_fisica");



                                    Pessoa dono_aux;
                                    //colocando os valores para pessoa ou para ong
                                    if(id_fisica_ == -1){
                                        PessoaJuridica dono_aux_ = new PessoaJuridica();
                                        dono_aux_.setCNPJ( jsonAux.getString("cnpj") );
                                        dono_aux_.setNomeResponsavel( jsonAux.getString("responsavel") );
                                        dono_aux_.setIdJuridica(  id_juridica_ );
                                        dono_aux_.setIdFisica(  id_fisica_ );

                                        dono_aux = dono_aux_;
                                    }else{
                                        PessoaFisica dono_aux_ = new PessoaFisica();
                                        dono_aux_.setCPF( jsonAux.getString("cpf"));
                                        dono_aux_.setIdFisica( id_fisica_ );
                                        dono_aux_.setIdJuridica(  -1 );
                                        dono_aux_.setIdJuridica(  id_juridica_ );
                                        dono_aux_.setIdFisica(  id_fisica_ );

                                        dono_aux = dono_aux_;
                                    }

                                    //colocando os valores de pessoas gerais, dos tipos que ong e pessoa fisica compartilham
                                    dono_aux.setId(  jsonAux.getInt("id_pessoa") );
                                    dono_aux.setEmail(jsonAux.getString("email"));
                                    dono_aux.setTelefone( jsonAux.getString("telefone") );
                                    dono_aux.setEndereco( jsonAux.getString("endereco"));
                                    dono_aux.setCidade( jsonAux.getString("cidade"));
                                    dono_aux.setEstado( jsonAux.getString("uf"));
                                    dono_aux.setNome( jsonAux.getString("nome"));

                                    aux.setDono(dono_aux);

                                    //adicionando Animal aux no vetor de animais
                                    animais.add(aux);
                                    //Notifica ao ArrayAdapter que o DataSet foi alterado
                                    animaisArrayAdapter.notifyDataSetChanged();
                                }

                            }else{
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSON EXCEPTION", Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_pessoa", id_pessoa);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        //Notifica ao ArrayAdapter que o DataSet foi alterado
        //animaisArrayAdapter.notifyDataSetChanged();
    }

}