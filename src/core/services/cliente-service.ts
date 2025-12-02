import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { env } from '../../environment/environment';
import { Observable } from 'rxjs';
import { Cliente } from '../models/cliente';

@Injectable({
  providedIn: 'root',
})
export class ClienteService {
  constructor(private httpClient: HttpClient) {}

  getClientes(): Observable<Cliente[]> {
    return this.httpClient.get<Cliente[]>(env.apiUrl + '/clientes');
  }

  create(cliente: Cliente): Observable<Cliente> {
    return this.httpClient.post<Cliente>(env.apiUrl + '/clientes', cliente);
  }

  update(cliente: Cliente): Observable<Cliente> {
    return this.httpClient.put<Cliente>(
      env.apiUrl + `/clientes/${cliente.id}`,
      cliente
    );
  }

  delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(env.apiUrl + `/clientes/${id}`);
  }
}

