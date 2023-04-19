import {Injectable} from "@angular/core";
import {HttpClient, HttpParams, HttpResponse} from "@angular/common/http";
import {ApplicationConfigService} from "../../core/config/application-config.service";
import {Observable} from "rxjs";
import {Calendar} from "./calendar.model";
import {EntityArrayResponseType} from "../event/service/event.service";
import {IEvent} from "../event/event.model";

@Injectable({ providedIn: 'root' })
export class CalendarService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/calendar');
  protected resourceUrlEvents = this.applicationConfigService.getEndpointFor('api/events/my');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(month: string, year: number): Observable<HttpResponse<Calendar>> {
    let options: HttpParams = new HttpParams();
    options = options.append('month', month);
    options = options.append('year', year);
    return this.http.get<Calendar>(`${this.resourceUrl}`, { params: options, observe: 'response' });
  }

  queryMyEvents(day: number, month: string, year: number): Observable<EntityArrayResponseType> {
    let options: HttpParams = new HttpParams();
    options = options.append('day', day);
    options = options.append('month', month);
    options = options.append('year', year);
    return this.http.get<IEvent[]>(this.resourceUrlEvents, { params: options, observe: 'response' });
  }
}
