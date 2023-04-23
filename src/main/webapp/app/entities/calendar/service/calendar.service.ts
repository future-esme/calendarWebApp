import {Injectable} from "@angular/core";
import {HttpClient, HttpParams, HttpResponse} from "@angular/common/http";
import {ApplicationConfigService} from "../../../core/config/application-config.service";
import {Observable} from "rxjs";
import {Calendar, EventDTO} from "../calendar.model";
import {EntityArrayResponseType, EntityResponseType, RestEvent} from "../../event/service/event.service";
import {IEvent} from "../../event/event.model";
import {ITag} from "../../user-tags/tag.model";

type RestOf<T extends EventDTO> = Omit<T, 'startTime' | 'endTime' | 'notificationTime' | 'eventDate'> & {
  startTime?: string | null;
  endTime?: string | null;
  notificationTime?: string | null;
  eventDate?: string | null;
};
@Injectable({ providedIn: 'root' })
export class CalendarService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/calendar');
  protected resourceUrlEvents = this.applicationConfigService.getEndpointFor('api/events');
  protected resourceUrlTags = this.applicationConfigService.getEndpointFor('api/user/tags');

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
    return this.http.get<IEvent[]>(`${this.resourceUrlEvents}/my`, { params: options, observe: 'response' });
  }

  queryAllTags(): Observable<EntityArrayResponseType> {
    return this.http.get<ITag[]>(`${this.resourceUrlTags}/all`, { observe: 'response' });
  }

  create(event: EventDTO): Observable<HttpResponse<EventDTO>> {
    return this.http.post<EventDTO>(this.resourceUrlEvents, this.convertDateFromClient(event), { observe: 'response' });
  }

  findEvent(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IEvent>(`${this.resourceUrlEvents}/${id}`, { observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrlEvents}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(event: EventDTO): RestOf<EventDTO> {
    console.warn(event)
    return {
      ...event,
      startTime: event.startTime?.toJSON() ?? null,
      endTime: event.endTime?.toJSON() ?? null,
      notificationTime: event.notificationTime?.toJSON() ?? null,
      eventDate: event.eventDate?.toJSON() ?? null,
    };
  }
}
