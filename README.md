# simulador-financiamento-veiculos

Instruções:
Uma concessionária deseja disponibilizar aos seus clientes uma tela para simular o financiamento de veículos.

Desenvolva uma aplicação Java Swing que permita ao usuário informar os dados do veículo, escolher as condições de financiamento e visualizar o resultado da simulação.

A interface deverá ser organizada utilizando diferentes LayoutManagers e deverá apresentar componentes adequados para cada tipo de informação.

Requisitos.

A tela deve ter campos para informar a Marca, Modelo, ano e o Valor do carro.

Modelo: Deve ser apresentado em um campo de lista de seleção única.
Modelo: Campo de texto livre
Ano: Deve ser apresentado em um campo de lista de seleção única. Compreende os anos de 2000 até 2026 em ordem decrescente.
Valor: Campo de texto livre
A tela deve ter uma opção de seleção única para indicar se o carro é novo ou usado. Caso for selecionado tipo novo, o painel com os dados do veículo usado não deve ser mostrado. Se for selecionado o tipo usado, deve apresentar um novo painel com os dados do veículo usado. Neste painel deve apresentar:

Quilometragem: Campo de texto livre
Proprietários: Campo de texto livre
No painel sobre financiamento, deve ser informado se possui entrada, o valor de entrada e a quantidade de parcelas.

Campo possui entrada: Campo de seleção simples. Se estiver selecionado deve mostrar o campo entrada. Caso contrário deve ocultá-lo.
Entrada: Campo de texto livre
Parcelas: Deve ser apresentado em um campo de lista de seleção única. Valores: 12,24,36,48 e 60.
Abaixo do painel de financiamento, deve ser apresentado os botões Limpar e Calcular.

Botão limpar: Reseta todos os valores informados no formulário
Botão Calcular: Realiza as validações de dados e o cálculo do financiamento.
Cálculo do financiamento: Para realizar o cálculo de financiamento, primeiramente os dados informados devem ser validados.

Deve ser utilizado uma fórmula simples do cálculo do financiamento.

 

valor_financiado = valor_veiculo - entrada.

valor_total = valor_financiado * (1+taxa)

valor_parcela = valor_total / numero_parcelas.

 

O painel de resultado deve ser mostrado apenas quando for calculado o financiamento. Deve ser apresentado:

Valor financiado
Valor da parcela
Total a pagar: valor da parcela * numero de parcelas

<img width="1205" height="2148" alt="image" src="https://github.com/user-attachments/assets/3832acc7-4495-45a1-94d2-6af24889dd71" />
