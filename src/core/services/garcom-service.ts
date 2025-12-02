import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { env } from "../../environment/environment";
import { Observable } from "rxjs";
import { Garcom } from "../models/garcom";

@Injectable({
  providedIn: "root",
})
export class GarcomService {
  constructor(private httpClient: HttpClient) {}

  getGarcons(): Observable<Garcom[]> {
    return this.httpClient.get<Garcom[]>(env.apiUrl + "/garcons");
  }

  create(garcom: Garcom): Observable<Garcom> {
    return this.httpClient.post<Garcom>(env.apiUrl + "/garcons", garcom);
  }

  update(garcom: Garcom): Observable<Garcom> {
    return this.httpClient.put<Garcom>(
      env.apiUrl + `/garcons/${garcom.id}`,
      garcom
    );
  }

  delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(env.apiUrl + `/garcons/${id}`);
  }
}
