import { Component, OnInit } from '@angular/core';
import { QuestionsService } from 'src/app/services/questions.service';
import { Question } from 'src/app/datamodel/question';
import { Location } from '@angular/common';
import { TopicService } from 'src/app/services/topic.service';
import { Topic } from 'src/app/datamodel/topic';
import { FormBuilder, Validators, FormArray, FormGroup } from '@angular/forms';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { User } from 'src/app/datamodel/user';

@Component({
  selector: 'app-question-form',
  templateUrl: './question-form.component.html',
  styleUrls: ['./question-form.component.css']
})
export class QuestionFormComponent implements OnInit {

  topics: Topic[];
  questionForm: FormGroup;
  private currentUser: User;

  constructor(private questionService: QuestionsService,
    private authService: AuthenticationService,
    private topicService: TopicService,
    private location: Location,
    private fb: FormBuilder) { 
      this.authService.currentUser.subscribe(x => this.currentUser = x);
    }

  ngOnInit() {
    this.questionForm = this.fb.group({
      question: this.fb.group({
        content: ['', Validators.required],
        topic: ['', Validators.required]
      }),
      options: this.fb.array([], [Validators.required])
    });
    this.topicService.getAllTopics().subscribe(topics => this.topics = topics);
  }

  get options() {
    return this.questionForm.get('options') as FormArray;
  }

  get content() {
    return this.questionForm.get('question').get('content');
  }

  get topic() {
    return this.questionForm.get('question').get('topic');
  }

  addOption() {
    let optionForm: FormGroup = this.fb.group({
      text: ['', Validators.required],
      is_right: [false, Validators.required]
    });
    this.options.push(optionForm);
  }

  removeOption(index: number) {
    this.options.removeAt(index);
  }

  goBack(): void {
    this.location.back();
  }

  createQuestion() {
    if (this.questionForm.valid) {
      let data = JSON.stringify(this.questionForm.value);
      this.questionService.createQuestionWithOptions(data).subscribe();
      //this.questionService.createQuestion(null).subscribe();
      this.goBack();
    } else {
      alert("form is invalid! please check your fields");
    }
  }


}
