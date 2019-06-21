    private void insereAnimal(){
        //fazendo a validacao dos dados nos campos

        //inicializando os valores
        final String nome = Nome.getText().toString();
        final String descricao = Descricao.getText().toString();
        final String idade = Idade.getText().toString().trim();
        final String raca = Raca.getText().toString();

        //valores dos spinners
        int pos = spinnerSexo.getSelectedItemPosition();
        final String sexo = spinnerSexo.getItemAtPosition(pos).toString().trim();
        pos = spinnerCondicao.getSelectedItemPosition();
        final String condicao = spinnerCondicao.getItemAtPosition(pos).toString().trim();
        pos = spinnerEspecie.getSelectedItemPosition();
        final String especie = spinnerEspecie.getItemAtPosition(pos).toString();


        //fazendo a transformacao da imagem para string encode64, para se poder mandar a imagem para o webServer
        Bitmap bitmap = BitmapFactory.decodeFile(imageURI.getPath() );
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] array = stream.toByteArray();
        final String image = Base64.encodeToString(array, 0);

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

        

        //fazendo o stringRequest para fazer o request ao WebServer
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            //se nao deu erro
                            if(!obj.getBoolean("error")){
                                Toast.makeText(getApplicationContext(), obj.getString("Animal Inserido com sucesso!"), Toast.LENGTH_LONG).show();

                                //mudando para a tela de meus animais cadastrados
                                Intent intent = new Intent(this, MeusPets.class);
                                startActivity(intent);

                            }else{
                                //imprime a mensagem de erro
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                //setando os valores para o Request
                Map<String, String> params = new HashMap<>();
                params.put("nome", nome);
                params.put("especie", especie);
                params.put("descricao", descricao);
                params.put("sexo", sexo);
                params.put("idade", idade);
                params.put("raca", raca);
                params.put("condicao", condicao);
                params.put("image",image);
                params.put("id_pessoa", id_pessoa);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }