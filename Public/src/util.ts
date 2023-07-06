export interface UserInfo {
    id: number | undefined;
    authority: number;
    name: string;
    username: string;
    password?: string;
    passwordAgain?: string;
}