import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PromotionService {

  constructor(private _http: HttpClient) { }

  getPromotions(): Observable<any> {
    return this._http.get<any>(environment.baseURL + `promotions`);
  }

  deletePromotion(id: number): Observable<any> {
    return this._http.delete<any>(environment.baseURL + `promotions/${id}`);
  }

  getUsers(id: number): Observable<any> {
    return this._http.get<any>(environment.baseURL + `offers/promotion/${id}`);
  }
}
