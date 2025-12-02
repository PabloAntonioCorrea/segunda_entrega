import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { env } from '../../environment/environment';
import { Observable } from 'rxjs';
import { Pedido, PedidoRequest, StatusPedido } from '../models/pedido';

@Injectable({
  providedIn: 'root',
})
export class PedidoService {
  constructor(private httpClient: HttpClient) {}

  getPedidos(): Observable<Pedido[]> {
    return this.httpClient.get<Pedido[]>(env.apiUrl + '/pedidos');
  }

  getPedidosPorStatus(status: StatusPedido): Observable<Pedido[]> {
    return this.httpClient.get<Pedido[]>(
      env.apiUrl + `/pedidos/status/${status}`
    );
  }

  create(pedido: PedidoRequest): Observable<Pedido> {
    return this.httpClient.post<Pedido>(env.apiUrl + '/pedidos', pedido);
  }

  updateStatus(id: number, status: StatusPedido): Observable<Pedido> {
    const params = new HttpParams().set('status', status);
    return this.httpClient.patch<Pedido>(
      env.apiUrl + `/pedidos/${id}/status`,
      null,
      { params }
    );
  }

  cancelar(id: number): Observable<void> {
    return this.httpClient.patch<void>(
      env.apiUrl + `/pedidos/${id}/cancelar`,
      null
    );
  }
}

