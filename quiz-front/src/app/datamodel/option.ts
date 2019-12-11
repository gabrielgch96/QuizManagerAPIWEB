import { Question } from './question';

export class Option{
    id: number;
    text: string;
    is_right: boolean;
    question: Question;

    constructor(text:string, is_right: boolean ){
        this.text = text;
        this.is_right = is_right;
    }
}