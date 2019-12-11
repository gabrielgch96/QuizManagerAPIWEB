import { User } from './user';
import { Answer } from './answer';

export class Submission{

    id: number;
    user: User;
    score: number;
    answers: Answer[];
    date: Date;

    constructor(){

    }
}