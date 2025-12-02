import { Cliente } from './cliente';
import { Garcom } from './garcom';
import { Prato } from './prato';

export enum StatusPedido {
  EM_ABERTO = 'EM_ABERTO',
  EM_PREPARACAO = 'EM_PREPARACAO',
  PRONTO = 'PRONTO',
  ENTREGUE = 'ENTREGUE',
  CANCELADO = 'CANCELADO'
}

export interface ItemPedido {
  id?: number;
  quantidade: number;
  subTotal?: number;
  prato: Prato;
}

export interface ItemPedidoRequest {
  pratoId: number;
  quantidade: number;
}

export interface Pedido {
  id?: number;
  data?: string;
  status: StatusPedido;
  valorTotal?: number;
  cliente: Cliente;
  garcom: Garcom;
  itens: ItemPedido[];
}

export interface PedidoRequest {
  clienteId: number;
  garcomId: number;
  itens: ItemPedidoRequest[];
}

