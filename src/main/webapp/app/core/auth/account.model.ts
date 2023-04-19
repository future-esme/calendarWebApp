export class Account {
  constructor(
    public activated: boolean,
    public authorities: string[],
    public email: string,
    public firstName: string | null,
    public langKey: string,
    public lastName: string | null,
    public login: string,
    public imageUrl: string | null
  ) {}
}

export class UserInfoSettings {
  constructor(
    public email: string,
    public firstName: string | null,
    public langKey: string,
    public lastName: string | null,
    public weekFirstDay: string,
    public weekNumber: boolean,
    public keepTrash: boolean,
    public emailLanguage: string
  ) {
  }
}
