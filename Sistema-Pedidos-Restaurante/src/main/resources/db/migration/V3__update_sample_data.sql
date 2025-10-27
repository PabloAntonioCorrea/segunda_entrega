-- Atualização dos dados de exemplo com informações mais realistas

-- Limpar dados antigos
DELETE FROM itens_pedido;
DELETE FROM pedidos;
DELETE FROM clientes;
DELETE FROM garcons;
DELETE FROM pratos;

-- Inserir novos dados mais realistas
INSERT INTO clientes (nome, email, telefone) VALUES
('Ana Beatriz Silva', 'ana.silva@gmail.com', '(11) 98765-4321'),
('Carlos Eduardo Santos', 'carlos.santos@hotmail.com', '(11) 99876-5432'),
('Mariana Oliveira', 'mariana.oliveira@yahoo.com', '(11) 97654-3210'),
('João Pedro Costa', 'joao.costa@outlook.com', '(11) 96543-2109'),
('Fernanda Lima', 'fernanda.lima@gmail.com', '(11) 95432-1098');

INSERT INTO garcons (nome, matricula) VALUES
('Roberto Carlos', 'GAR0001'),
('Patricia Mendes', 'GAR0002'),
('Antonio Silva', 'GAR0003'),
('Juliana Santos', 'GAR0004');

INSERT INTO pratos (nome, preco) VALUES
('Pizza Margherita', 45.90),
('Pizza Calabresa', 42.50),
('Hambúrguer Artesanal', 38.90),
('Salada Caesar', 28.00),
('Sopa de Legumes', 18.50),
('Bife à Parmegiana', 42.00),
('Frango Grelhado', 35.90),
('Sobremesa do Chef', 15.00),
('Refrigerante Lata', 8.50),
('Suco Natural', 12.00);
