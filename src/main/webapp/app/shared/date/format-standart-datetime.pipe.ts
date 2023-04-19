import { Pipe, PipeTransform } from '@angular/core';

import dayjs from 'dayjs';

@Pipe({
  name: 'formatStandartDateTime',
})
export class FormatStandartDateTimePipe implements PipeTransform {
  transform(day: dayjs.Dayjs | null | undefined): string {
    return day ? dayjs(day).format('DD.MM.YYYY HH:mm') : '';
  }
}
