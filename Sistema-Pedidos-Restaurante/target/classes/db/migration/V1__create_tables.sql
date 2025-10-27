-- Criação das tabelas do sistema de pedidos de restaurante

-- Tabela de clientes
CREATE TABLE clientes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefone VARCHAR(20) NOT NULL
);

-- Tabela de garçons
CREATE TABLE garcons (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    matricula VARCHAR(10) NOT NULL UNIQUE
);

-- Tabela de pratos
CREATE TABLE pratos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL
);

-- Tabela de pedidos
CREATE TABLE pedidos (
    id BIGSERIAL PRIMARY KEY,
    data TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL,
    id_cliente BIGINT NOT NULL,
    id_garcom BIGINT NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id),
    FOREIGN KEY (id_garcom) REFERENCES garcons(id)
);

-- Tabela de itens do pedido
CREATE TABLE itens_pedido (
    id BIGSERIAL PRIMARY KEY,
    quantidade INTEGER NOT NULL,
    sub_total DECIMAL(10,2) NOT NULL,
    id_pedido BIGINT NOT NULL,
    id_prato BIGINT NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id),
    FOREIGN KEY (id_prato) REFERENCES pratos(id)
);

-- Índices para melhor performance
CREATE INDEX idx_pedidos_cliente ON pedidos(id_cliente);
CREATE INDEX idx_pedidos_garcom ON pedidos(id_garcom);
CREATE INDEX idx_pedidos_status ON pedidos(status);
CREATE INDEX idx_pedidos_data ON pedidos(data);
CREATE INDEX idx_itens_pedido_pedido ON itens_pedido(id_pedido);
CREATE INDEX idx_itens_pedido_prato ON itens_pedido(id_prato);
