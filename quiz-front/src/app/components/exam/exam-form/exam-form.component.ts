import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { Question } from 'src/app/datamodel/question';
import { ExamService } from 'src/app/services/exam.service';
import { ActivatedRoute } from '@angular/router';
import { QuestionsService } from 'src/app/services/questions.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { User } from 'src/app/datamodel/user';

@Component({
  selector: 'app-exam-form',
  templateUrl: './exam-form.component.html',
  styleUrls: ['./exam-form.component.css']
})
export class ExamFormComponent implements OnInit {

  examForm: FormGroup;
  private currentUser: User;

  constructor(private location: Location,
    private authService: AuthenticationService,
    private fb: FormBuilder,
    private examService: ExamService,
    private questionService: QuestionsService) {
      this.authService.currentUser.subscribe(x => this.currentUser = x);
     }

  ngOnInit() {
    this.examForm = this.fb.group({
      exam: this.fb.group({
        title: ['', Validators.required]
      }),
      exam_questions: this.fb.array([], [Validators.required])
    });
  }

  onAdded(question: Question) {
    if (!this.checkIfAdded(question))
      this.addQuestion(question);
    else
      alert("Question already added");
  }

  checkIfAdded(question: Question): boolean {

    for (let questionIn of this.exam_questions.controls) {
      if (questionIn.get('id').value == question.id)
        return true;
    }
    return false;
  }

  get title() {
    return this.examForm.get('exam').get('title');
  }

  get exam_questions() {
    return this.examForm.get('exam_questions') as FormArray;
  }

  goBack(): void {
    this.location.back();
  }

  createExam(): void {
    if (this.examForm.valid) {
      let data = JSON.stringify(this.examForm.value);
      console.log(data);
      this.examService.createExamWithQuestions(data).subscribe();
      this.goBack();
    }
    else {
      alert('please verify the fields!...At least one question is needed')
    }
  }

  addQuestion(question: Question) {
    let questionForm: FormGroup = this.fb.group({
      id: [question.id],
      content: [question.content, Validators.required],
      topic: this.fb.group({
        id: [question.topic.id],
        name: [question.topic.name, Validators.required]
      })
    })
    this.exam_questions.push(questionForm);
  }

  removeOption(index: number) {
    this.exam_questions.removeAt(index);
  }

  getRandom(){
    this.exam_questions.clear();
    let random = this.questionService.getRandom().subscribe(
      questions=> questions.forEach(question => {
        this.addQuestion(question);
      })
    )
  }

}
