import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { env } from '../../environment/environment';
import { Observable } from 'rxjs';
import { Prato } from '../models/prato';

@Injectable({
  providedIn: 'root',
})
export class PratoService {
  constructor(private httpClient: HttpClient) {}

  getPratos(): Observable<Prato[]> {
    return this.httpClient.get<Prato[]>(env.apiUrl + '/pratos');
  }

  create(prato: Prato): Observable<Prato> {
    return this.httpClient.post<Prato>(env.apiUrl + '/pratos', prato);
  }

  update(prato: Prato): Observable<Prato> {
    return this.httpClient.put<Prato>(
      env.apiUrl + `/pratos/${prato.id}`,
      prato
    );
  }

  delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(env.apiUrl + `/pratos/${id}`);
  }
}

